package huitca1212.galicianweather.domain

abstract class UseCase<T> {

    var useCaseParams: UseCaseParams = NoUseCaseParams

    abstract suspend fun run(listener: Callback<T>)

    infix fun withParams(useCaseParams: UseCaseParams) = apply {
        this.useCaseParams = useCaseParams
    }
}

abstract class UseCaseParams {
    open val remoteUseCaseParams: RemoteUseCaseParams = NoRemoteUseCaseParams
    open val localUseCaseParams: LocalUseCaseParams = NoLocalUseCaseParams
}

object NoUseCaseParams : UseCaseParams()

abstract class RemoteUseCaseParams {
    open var map = mutableMapOf<String, String>()
}

abstract class LocalUseCaseParams

object NoRemoteUseCaseParams : RemoteUseCaseParams()

object NoLocalUseCaseParams : LocalUseCaseParams()
