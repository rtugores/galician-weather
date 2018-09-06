package huitca1212.galicianweather.view.base

import test.kotlin.clean.ficiverson.presentation.IBasePresenter
import java.lang.ref.WeakReference

/**
 * Base class for the presenter.
 */
abstract class BasePresenter<out T>
/**
 * The base constructor with the view translator.

 * @param translatorView The view translator of the MVP.
 */
    (private val translatorView: WeakReference<T>) : IBasePresenter {

    /**
     * Provides the view.

     * @return The current view.
     */
    val view: T?
        get() = translatorView.get()

    override fun onCreate() {}

    override fun onReady() {}

    override fun onStart() {}

    override fun onResume() {}

    override fun onPause() {}

    override fun onStop() {}

    override fun onDestroy() {}

    override fun onRestoreInstanceState() {}

    override fun onSaveInstanceState() {}

    override fun onActivityResult() {}
}
