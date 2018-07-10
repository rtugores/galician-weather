package huitca1212.tiempoourense.business


//data class Result<T>(var response: T? = null, var exception: Exception? = null)

@Suppress("unused")
interface Result<T>

data class Success<T>(val response: T) : Result<T>
data class Error<T>(val exception: Exception = Exception()) : Result<T>