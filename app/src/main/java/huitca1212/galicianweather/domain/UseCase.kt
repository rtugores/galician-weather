package huitca1212.galicianweather.domain

interface UseCase<P, T> {
    suspend fun run(policy: DataPolicy, params: P): Result<T>
}