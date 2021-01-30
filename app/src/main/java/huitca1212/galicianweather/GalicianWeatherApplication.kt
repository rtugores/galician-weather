package huitca1212.galicianweather

import android.app.Application
import huitca1212.galicianweather.injection.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin

@KoinApiExtension
class GalicianWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GalicianWeatherApplication)
            modules(
                listOf(
                    AppModules.networkModule,
                    AppModules.homeModule,
                    AppModules.stationDetailsModule
                )
            )
        }
    }
}
