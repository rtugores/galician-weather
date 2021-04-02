package com.galicianweather.network

import com.galicianweather.data.datasource.model.DataDailyWrapper
import com.galicianweather.data.datasource.model.DataLastMinutesWrapper
import org.koin.core.component.KoinApiExtension
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

@KoinApiExtension
interface StationApi {

    @GET("ultimos10minEstacionsMeteo.action")
    fun getLastMinutesDataStation(@QueryMap map: Map<String, String>): Call<DataLastMinutesWrapper>

    @GET("datosDiariosEstacionsMeteo.action")
    fun getDailyInfo(@QueryMap map: Map<String, String>): Call<DataDailyWrapper>
}
