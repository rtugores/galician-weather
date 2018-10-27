package huitca1212.galicianweather.domain


@Suppress("unused")
sealed class Result<out T>(val dataStatus: DataStatus = DataStatus.ERROR)

data class Success<T>(val data: T, val status: DataStatus) : Result<T>(status)
data class NoInternetError(val error: Exception = Exception(), val message: String? = null) : Result<Nothing>()
data class UnknownError(val error: Exception = Exception(), val message: String? = null) : Result<Nothing>()
data class NoData(val error: Exception = Exception()) : Result<Nothing>()

enum class DataStatus {
    LOCAL, REMOTE, ERROR
}