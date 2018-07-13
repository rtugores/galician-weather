package huitca1212.tiempoourense.network

import huitca1212.tiempoourense.model.DataDailyWrapper
import huitca1212.tiempoourense.model.DataLastMinutesWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StationApi {
    @GET("ultimos10minEstacionsMeteo.action")
    fun getLastMinutesDataStation(@Query("idEst") stationId: String): Call<DataLastMinutesWrapper>

    @GET("datosDiariosEstacionsMeteo.action")
    fun getDailyInfo(@Query("idEst") stationId: String): Call<DataDailyWrapper>
}