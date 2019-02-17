package huitca1212.galicianweather.domain


@Suppress("unused")
sealed class Result<out T>(val dataStatus: DataStatus)

data class Success<T>(val data: T, val status: DataStatus) : Result<T>(status)
data class NoInternetError(val error: Exception = Exception(), val message: String? = null) : Result<Nothing>(DataStatus.ERROR)
data class UnknownError(val error: Exception = Exception(), val message: String? = null) : Result<Nothing>(DataStatus.ERROR)
data class Multiple<T>(val data: T) : Result<T>(DataStatus.MULTIPLE)

enum class DataStatus {
    LOCAL, REMOTE, MULTIPLE, ERROR
}