package huitca1212.galicianweather.injection

import android.app.Activity
import android.util.Log
import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.domain.DailyInfoUseCase
import huitca1212.galicianweather.domain.LastMinutesInfoUseCase
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.view.HomePresenter
import huitca1212.galicianweather.view.StationDetailsPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.inject
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T> Activity.injectActivity(): Lazy<T> =
    inject(parameters = { mapOf(AppModules.ACTIVITY_PARAM to this) })

object AppModules {

    const val ACTIVITY_PARAM = "activity"

    val homeModule = applicationContext {
        factory { HomePresenter(it[ACTIVITY_PARAM]) }
    }

    val stationDetailsModule = applicationContext {
        factory { LastMinutesInfoNetworkDataSource(get()) }
        factory { DailyInfoNetworkDataSource(get()) }
        factory { LastMinutesInfoUseCase(get()) }
        factory { DailyInfoUseCase(get()) }
        factory { StationDetailsPresenter(it[ACTIVITY_PARAM], get(), get()) }
    }

    val networkModule = applicationContext {
        bean { stationApi(get()) }
        bean { retrofit(get()) }
        bean { okHttpClient() }
    }

    private fun stationApi(retrofit: Retrofit): StationApi =
        retrofit.create(StationApi::class.java)

    private fun retrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("http://servizos.meteogalicia.gal/rss/observacion/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun okHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message -> Log.d("Interceptor", message) }.apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
}