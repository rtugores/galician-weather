package huitca1212.tiempoourense.service

import huitca1212.tiempoourense.model.DataDailyWrapper
import huitca1212.tiempoourense.model.DataLastMinutesWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("ultimos10minEstacionsMeteo.action")
    fun getLastMinutesDataStation(@Query("idEst") stationId: String): Call<DataLastMinutesWrapper>

    @GET("datosDiariosEstacionsMeteo.action")
    fun getDailyDataStation(@Query("idEst") stationId: String): Call<DataDailyWrapper>
}