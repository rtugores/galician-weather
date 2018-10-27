package huitca1212.galicianweather.domain

abstract class UseCase<T> {

    var useCaseParams: UseCaseParams = NoUseCaseParams

    abstract suspend fun run(listener: ((Result<T>) -> Unit))

    infix fun withParams(useCaseParams: UseCaseParams) = also {
        this.useCaseParams = useCaseParams
    }
}

abstract class UseCaseParams {
    open val remoteUseCaseParams: RemoteUseCaseParams = NoRemoteUseCaseParams
    open val localUseCaseParams: LocalUseCaseParams = NoLocalUseCaseParams
}

object NoUseCaseParams : UseCaseParams()

interface RemoteUseCaseParams {
    var map: MutableMap<String, String>

    fun map(): MutableMap<String, String> = map

    operator fun invoke() = map()
}

interface LocalUseCaseParams

object NoRemoteUseCaseParams : RemoteUseCaseParams {
    override var map = mutableMapOf<String, String>()
}

object NoLocalUseCaseParams : LocalUseCaseParams
