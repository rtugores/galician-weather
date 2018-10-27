package huitca1212.galicianweather.domain

data class GetRemoteStationsUseCaseParams(
    val _idEst: String
) : RemoteUseCaseParams() {

    private var idEst: String by map

    init {
        idEst = _idEst
    }
}

data class GetStationsUseCaseParams(
    override val remoteUseCaseParams: GetRemoteStationsUseCaseParams
) : UseCaseParams()
