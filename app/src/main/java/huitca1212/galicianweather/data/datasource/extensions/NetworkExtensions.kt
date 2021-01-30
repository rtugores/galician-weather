package huitca1212.galicianweather.data.datasource.extensions

import huitca1212.galicianweather.data.repository.GWHttpCodeException
import huitca1212.galicianweather.data.repository.GWNetworkException
import huitca1212.galicianweather.data.repository.GWTimeoutException
import retrofit2.Call
import java.io.IOException
import java.net.SocketTimeoutException

@Throws(Exception::class)
fun <T> Call<T>.executeCall(): T? {
    return try {
        execute().run {
            if (isSuccessful) {
                body()
            } else {
                throw GWHttpCodeException(errorBody()?.string().orEmpty(), code())
            }
        }
    } catch (e: SocketTimeoutException) {
        throw GWTimeoutException(e)
    } catch (e: IOException) {
        throw GWNetworkException(e)
    }
}
