package huitca1212.tiempoourense.business

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.withContext


interface IResolver<T> {
    suspend fun resolveUi(callback: (Result<T>) -> Unit, result: Result<T>) {
        withContext(UI) { callback(result) }
    }
}