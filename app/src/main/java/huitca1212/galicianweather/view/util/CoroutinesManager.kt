package huitca1212.galicianweather.view.util

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.NonCancellable.invokeOnCompletion
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class CoroutinesManager {

    private var jobs = mutableListOf<Job>()

    fun cancelAll() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    fun add(job: Job) {
        jobs.add(job)
    }

    fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        launch(UI) { block() }.also { job ->
            add(job)
            invokeOnCompletion {
                jobs.remove(job)
            }
        }
    }
}