package huitca1212.galicianweather.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

typealias Callback<T> = (Result<T>) -> Unit

class UseCaseInvoker(private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    private val asyncJobs = mutableListOf<Job>()

    @Suppress("unchecked_cast")
    fun <T> execute(useCase: UseCase<T>, result: Callback<T>?) {
        executeMultiple(useCase, executeSimultaneously = false) {
            result?.invoke(it as Result<T>)
        }
    }

    fun executeMultiple(vararg useCases: UseCase<*>, executeSimultaneously: Boolean = true, result: Callback<*>?) {
        val useCasesSize = AtomicInteger(useCases.size)
        launchAndCompletion {
            try {
                executeUseCases(
                    *useCases,
                    executeSimultaneously = executeSimultaneously
                ) {
                    result?.invoke(it)
                    if (useCasesSize.decrementAndGet() == 0) {
                        result?.invoke(Finish)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun arePendingTasks() = asyncJobs.size > 0

    fun cancelAllTasks() {
        asyncJobs.takeIf {
            arePendingTasks()
        }?.forEach {
            it.cancel()
        }
        asyncJobs.clear()
    }

    private fun launchAndCompletion(block: suspend CoroutineScope.() -> Unit) {
        val job: Job = GlobalScope.async(contextProvider.main) { block() }
        asyncJobs.add(job)
        job.invokeOnCompletion { _ ->
            /**
             * On cancellation might be thrown a ConcurrentException due to multiple manipulation of [asyncJobs]
             */
            job.takeUnless { it.isCancelled }?.run {
                asyncJobs.remove(this)
            }
        }
    }

    private suspend fun <T> runUseCase(defaultInit: Boolean, block: suspend CoroutineScope.() -> T): Job {
        return GlobalScope.launch(
            kotlin.coroutines.coroutineContext + contextProvider.background,
            if (defaultInit) CoroutineStart.DEFAULT else CoroutineStart.LAZY
        ) {
            block()
        }
    }

    private suspend fun executeUseCases(vararg useCases: UseCase<*>, executeSimultaneously: Boolean, listener: Callback<*>) {
        val jobs = mutableListOf<Job>()
        useCases.forEach { useCase ->
            val job = runUseCase(executeSimultaneously) {
                useCase.run {
                    runBlocking(contextProvider.main) {
                        if (arePendingTasks()) {
                            listener(it)
                        }
                    }
                }
            }
            jobs.add(job)
        }

        jobs.map {
            //If simultaneous execution was not selected the coroutine(s) was(were) init by lazy, so there won't be initialized until start()
            if (!executeSimultaneously) it.start()
            it.join()
        }
    }
}

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Main }
    open val background: CoroutineContext by lazy { Default }
}