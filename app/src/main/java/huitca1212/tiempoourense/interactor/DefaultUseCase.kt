package huitca1212.tiempoourense.interactor

import kotlinx.coroutines.experimental.async
import retrofit2.Call
import java.io.IOException


abstract class DefaultUseCase<out T> {

    fun execute() = async {
        try {
            val response = call().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    Success(it)
                } ?: Error()
            } else {
                Error()
            }
        } catch (e: IOException) {
            IOError(e)
        } catch (e: Exception) {
            Error(e)
        }
    }

    abstract fun call(): Call<out T>
}
