package huitca1212.galicianweather.domain

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.coroutineContext

class UseCaseInvoker(private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    fun isPendingTask(): Boolean = asyncJobs.size != 0

    fun <P, T> execute(
        useCase: UseCase<P, T>,
        params: P,
        policy: DataPolicy,
        onResult: ((Result<T>) -> Unit)?
    ) {
        launchAsync {
            try {
                when (policy) {
                    DataPolicy.Local, DataPolicy.Network -> onResult?.invoke(asyncAwait { useCase.run(policy, params) })
                    DataPolicy.LocalAndNetwork -> {
                        onResult?.invoke(asyncAwait { useCase.run(DataPolicy.Local, params) })
                        onResult?.invoke(asyncAwait { useCase.run(DataPolicy.Network, params) })
                    }
                }
            } catch (e: Exception) {
                onResult?.invoke(Error())
            }
        }
    }

    fun executeParallel(
        useCases: Map<UseCase<String, out Any>, String>,
        policy: DataPolicy,
        onResult: ((Result<out Any>) -> Unit)?,
        onFinish: ((Result<out Any>) -> Unit)?
    ) {
        launchAsync {
            try {
                val results = mutableListOf<Deferred<Result<out Any>>>()
                useCases.forEach{ (useCase, params) ->
                    when (policy) {
                        DataPolicy.Local, DataPolicy.Network -> results.add(async { useCase.run(policy, params) })
                        DataPolicy.LocalAndNetwork -> {
                            results.add(async { useCase.run(DataPolicy.Local, params) })
                            results.add(async { useCase.run(DataPolicy.Network, params) })
                        }
                    }

                }
                val resultsToNotify = mutableListOf<Result<out Any>>()
                results.forEach {
                    resultsToNotify.add(it.await())
                }
                resultsToNotify.forEach {
                    onResult?.invoke(it)
                }
                resultsToNotify.forEach {
                    if (it is NoInternetError || it is Error) {
                        onFinish?.invoke(it)
                        return@launchAsync
                    }
                }
                onFinish?.invoke(Success(Unit))
            } catch (e: Exception) {
                onFinish?.invoke(Error(e))
            }
        }
    }

    fun cancelAllAsync() {
        val asyncJobsSize = asyncJobs.size

        if (asyncJobsSize > 0) {
            for (i in 0 until asyncJobsSize) {
                asyncJobs[i].cancel()
            }
        }
        asyncJobs.clear()
    }

    private fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        val job: Job = launch(contextProvider.main) { block() }
        asyncJobs.add(job)
        job.invokeOnCompletion { asyncJobs.remove(job) }
    }


    private suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(coroutineContext + contextProvider.background) { block() }
    }

    private suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
        return async(block).await()
    }
}

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
    open val background: CoroutineContext by lazy { DefaultDispatcher }
}