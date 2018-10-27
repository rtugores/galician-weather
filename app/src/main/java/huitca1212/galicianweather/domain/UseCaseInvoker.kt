package huitca1212.galicianweather.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext

class UseCaseInvoker(private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    private val asyncJobs = mutableListOf<Job>()

    @Suppress("unchecked_cast")
    fun <T> execute(useCase: UseCase<T>, result: ((Result<T>) -> Unit)?) {
        executeMultiple(useCase, executeSimultaneously = false) {
            result?.invoke(it as Result<T>)
        }
    }

    fun executeMultiple(vararg useCases: UseCase<*>, executeSimultaneously: Boolean = true, result: ((Result<*>) -> Unit)?) {
        launchAndCompletion {
            try {
                executeUseCases(
                    *useCases,
                    executeSimultaneously = executeSimultaneously
                ) {
                    result?.invoke(it)
                }
            } catch (ex: Exception) {
                result?.invoke(UnknownError(ex))
            }
        }
    }

    fun isPendingTask(): Boolean = asyncJobs.size > 0

    fun cancelAllAsync() {
        asyncJobs.takeIf {
            isPendingTask()
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
            job.takeUnless { it.isCancelled }?.run { asyncJobs.remove(this) }
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

    private suspend fun executeUseCases(vararg useCases: UseCase<*>, executeSimultaneously: Boolean, listener: (Result<*>) -> Unit) {
        val jobs = mutableListOf<Job>()
        useCases.forEach { useCase ->
            val job = runUseCase(executeSimultaneously) {
                useCase.run {
                    if (isPendingTask()) {
                        runBlocking(contextProvider.main) {
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