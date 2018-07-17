package huitca1212.galicianweather.usecase

import kotlinx.coroutines.experimental.async
import java.io.IOException
import kotlin.coroutines.experimental.CoroutineContext


abstract class BaseUseCase<P, T>(private val coroutineContext: CoroutineContext) {

    fun execute(params: P) = async(coroutineContext) {
        try {
            repositoryCall(params)
        } catch (e: IOException) {
            IOError(e)
        } catch (e: Exception) {
            Error(e)
        }
    }

    abstract fun repositoryCall(params: P): Success<T>
}