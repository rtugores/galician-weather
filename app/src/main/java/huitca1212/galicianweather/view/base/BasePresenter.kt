package huitca1212.galicianweather.view.base

/**
 * Base class for the presenter.
 */
abstract class BasePresenter {

    open fun onCreate() {}

    open fun onPostCreate() {}

    open fun onStart() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

    open fun onDestroy() {}

    open fun onRestoreInstanceState() {}

    open fun onSaveInstanceState() {}

    open fun onActivityResult() {}
}
