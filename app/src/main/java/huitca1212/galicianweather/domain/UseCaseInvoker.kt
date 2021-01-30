package huitca1212.galicianweather.domain

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class UseCaseInvoker(private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    private var parentJob: Job? = null
    private var useCasesAmount = AtomicInteger(0)

    fun executeParallel(vararg useCases: UseCase<*, *>, result: Callback<*>?) {
        GlobalScope.launch(contextProvider.main) {
            parentJob = coroutineContext[Job]
            try {
                useCasesAmount.getAndAdd(useCases.size)
                executeUseCases(*useCases) {
                    result?.invoke(it)
                }
            } catch (e: CancellationException) {
                // Do nothing
            } catch (e: Exception) {
                result?.invoke(Error(e))
            }
        }
    }

    fun cancelAllTasks() {
        parentJob?.cancel()
        parentJob = null
        useCasesAmount.getAndSet(0)
    }

    private suspend fun executeUseCases(vararg useCases: UseCase<*, *>, listener: Callback<*>) {
        val childJobs = mutableListOf<Job>()
        val results = mutableListOf<Result<*>>()

        useCases.forEach { useCase ->
            val childJob = GlobalScope.launch(coroutineContext + contextProvider.background, CoroutineStart.DEFAULT) {
                useCase.run {
                    handleResult(it, results, listener)
                }
            }
            childJobs.add(childJob)
        }

        childJobs.forEach {
            it.join()
        }
    }

    private fun handleResult(result: Result<*>, results: MutableList<Result<*>>, listener: Callback<*>) {
        runBlocking(contextProvider.main) {
            parentJob?.let {
                if (useCasesAmount.getAndDecrement() > 0) {
                    results.add(result)
                    if (useCasesAmount.get() == 0) {
                        listener(Multiple(results))
                    }
                }
            }
        }
    }
}

class CoroutineContextProvider {

    val main: CoroutineContext by lazy { Main }
    val background: CoroutineContext by lazy { Default }
}
