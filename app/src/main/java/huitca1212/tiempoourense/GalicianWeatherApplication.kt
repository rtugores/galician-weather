package huitca1212.tiempoourense

import android.app.Application
import huitca1212.tiempoourense.injection.AppModules
import org.koin.android.ext.android.startKoin

class GalicianWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(AppModules().networkModule))
    }
}
