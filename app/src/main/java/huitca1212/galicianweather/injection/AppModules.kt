package huitca1212.galicianweather.injection

import android.util.Log
import huitca1212.galicianweather.network.StationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppModules {

    val networkModule: Module = applicationContext {
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