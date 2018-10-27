package huitca1212.galicianweather.domain

data class GetRemoteStationsUseCaseParams(
    val _idEst: String
) : RemoteUseCaseParams {

    override var map = mutableMapOf<String, String>()

    private var idEst: String by map

    init {
        idEst = _idEst
    }
}

data class GetStationsUseCaseParams(
    override val remoteUseCaseParams: GetRemoteStationsUseCaseParams
) : UseCaseParams()
