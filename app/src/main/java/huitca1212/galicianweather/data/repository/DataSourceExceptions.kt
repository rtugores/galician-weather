package huitca1212.galicianweather.data.repository

// Remote data source exceptions

class GWHttpCodeException(val errorMessage: String, val errorCode: Int) : GWNetworkException(Exception())

class GWTimeoutException(e: Exception) : GWNetworkException(e)

class GWNullBodyException : Exception()

open class GWNetworkException(e: Exception? = null) : Exception(e)