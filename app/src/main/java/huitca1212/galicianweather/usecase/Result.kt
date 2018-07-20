package huitca1212.galicianweather.usecase


@Suppress("unused")
interface Result<T>

data class Success<T>(val response: T) : Result<T>
data class NoInternetError<T>(val exception: Exception = Exception()) : Result<T>
data class Error<T>(val exception: Exception = Exception()) : Result<T>