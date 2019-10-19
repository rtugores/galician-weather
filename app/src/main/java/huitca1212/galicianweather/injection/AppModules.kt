package huitca1212.galicianweather.injection

import android.app.Activity
import android.util.Log
import huitca1212.galicianweather.data.datasource.DailyInfoRemoteDataSource
import huitca1212.galicianweather.data.datasource.LastMinutesInfoRemoteDataSource
import huitca1212.galicianweather.data.repository.WeatherRepository
import huitca1212.galicianweather.domain.DailyInfoUseCase
import huitca1212.galicianweather.domain.LastMinutesInfoUseCase
import huitca1212.galicianweather.domain.UseCaseInvoker
import huitca1212.galicianweather.network.StationApi
import huitca1212.galicianweather.view.HomePresenter
import huitca1212.galicianweather.view.StationDetailsPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T : Any> Activity.injectActivity(): Lazy<T> =
    inject { parametersOf(this) }

object AppModules {

    private const val timeout = 30L

    val homeModule = module {
        factory { HomePresenter(it[0]) }
    }

    val stationDetailsModule = module {
        factory { LastMinutesInfoRemoteDataSource() }
        factory { DailyInfoRemoteDataSource() }

        factory { WeatherRepository(get(), get()) }

        factory { LastMinutesInfoUseCase(get()) }
        factory { DailyInfoUseCase(get()) }

        factory { UseCaseInvoker() }

        factory { StationDetailsPresenter(it[0], get(), get(), get()) }
    }

    val networkModule = module {
        single { stationApi(get()) }
        single { retrofit(get()) }
        single { okHttpClient() }
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
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.d(
                    "Interceptor",
                    message
                )
            }.apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()
}