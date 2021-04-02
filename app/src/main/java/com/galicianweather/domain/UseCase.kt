package com.galicianweather.domain

typealias Callback<T> = (Result<T>) -> Unit

abstract class UseCase<P: Any, T> {

    protected lateinit var useCaseParams: P

    abstract suspend fun run(listener: Callback<T>)

    infix fun withParams(useCaseParams: P) = apply {
        this.useCaseParams = useCaseParams
    }
}
