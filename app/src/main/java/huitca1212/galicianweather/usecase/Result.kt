package huitca1212.galicianweather.usecase


@Suppress("unused")
interface Result<T>

data class Success<T>(val response: T) : Result<T>
data class NoInternetError(val exception: Exception = Exception()) : Result<Nothing>
data class Error(val exception: Exception = Exception()) : Result<Nothing>