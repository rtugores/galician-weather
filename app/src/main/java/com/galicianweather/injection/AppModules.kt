package com.galicianweather.injection

import android.app.Activity
import android.util.Log
import com.galicianweather.data.datasource.DailyInfoRemoteDataSource
import com.galicianweather.data.datasource.LastMinutesInfoRemoteDataSource
import com.galicianweather.data.repository.WeatherRepository
import com.galicianweather.domain.GetDailyInfoUseCase
import com.galicianweather.domain.GetLastMinutesInfoUseCase
import com.galicianweather.domain.UseCaseInvoker
import com.galicianweather.network.StationApi
import com.galicianweather.view.HomePresenter
import com.galicianweather.view.StationDetailsPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

inline fun <reified T : Any> Activity.injectActivity(): Lazy<T> =
    inject { parametersOf(this) }

@KoinApiExtension
object AppModules {

    private const val timeout = 30L

    val homeModule = module {
        factory { HomePresenter(it[0]) }
    }

    val stationDetailsModule = module {
        factory { LastMinutesInfoRemoteDataSource() }
        factory { DailyInfoRemoteDataSource() }

        factory { WeatherRepository(get(), get()) }

        factory { GetLastMinutesInfoUseCase(get()) }
        factory { GetDailyInfoUseCase(get()) }

        factory { UseCaseInvoker() }

        factory { StationDetailsPresenter(it[0], get(), get(), get()) }
    }

    val networkModule = module {
        single { stationApi(get()) }
        single { retrofit(get()) }
        single { okHttpClient() }
    }

    private fun stationApi(retrofit: Retrofit): StationApi = retrofit.create(StationApi::class.java)

    private fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://servizos.meteogalicia.gal/rss/observacion/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.d("Interceptor", message)
            }.apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }
}
