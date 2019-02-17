package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoRemoteDataSource
import huitca1212.galicianweather.data.datasource.LastMinutesInfoRemoteDataSource
import huitca1212.galicianweather.domain.DataStatus
import huitca1212.galicianweather.domain.Error
import huitca1212.galicianweather.domain.Success

class WeatherRepository(
    private val dailyInfoRemoteDataSource: DailyInfoRemoteDataSource,
    private val lastMinutesInfoRemoteDataSource: LastMinutesInfoRemoteDataSource
) {

    fun getDailyInfo(stationId: String) =
        try {
            val dailyInfo = dailyInfoRemoteDataSource.getDailyInfo(stationId)
            Success(dailyInfo, DataStatus.REMOTE)
        } catch (e: Exception) {
            Error(e)
        }

    fun getLastMinutesInfo(stationId: String) =
        try {
            val dailyInfo = lastMinutesInfoRemoteDataSource.getLastMinutesInfo(stationId)
            Success(dailyInfo, DataStatus.REMOTE)
        } catch (e: Exception) {
            Error(e)
        }
}