package huitca1212.galicianweather.usecase

import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext


abstract class BaseUseCase<in P, T>(private val coroutineContext: CoroutineContext) {

    fun execute(params: P) = async(coroutineContext) {
        try {
            repositoryCall(params)
        } catch (e: Exception) {
            Error(e)
        }
    }

    abstract fun repositoryCall(params: P): Result<out T>
}