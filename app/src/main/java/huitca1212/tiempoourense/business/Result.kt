package huitca1212.tiempoourense.business

import java.io.IOException


@Suppress("unused")
sealed class Result<T>

data class Success<T>(val response: T) : Result<T>()
data class IOError(val exception: IOException) : Result<Nothing>()
data class Error(val exception: Exception = Exception()) : Result<Nothing>()