package huitca1212.galicianweather.usecase

import java.io.IOException


@Suppress("unused")
interface Result<T>

data class Success<T>(val response: T) : Result<T>
data class IOError<T>(val exception: IOException) : Result<T>
data class Error<T>(val exception: Exception = Exception()) : Result<T>