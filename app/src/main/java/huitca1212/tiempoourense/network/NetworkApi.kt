package huitca1212.tiempoourense.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkApi {

    private const val BASE_URL = "http://servizos.meteogalicia.gal/rss/observacion/"

    var stationApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StationApi::class.java)!!
}