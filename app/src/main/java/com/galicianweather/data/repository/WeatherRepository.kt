package com.galicianweather.data.repository

import com.galicianweather.data.datasource.DailyInfoRemoteDataSource
import com.galicianweather.data.datasource.LastMinutesInfoRemoteDataSource
import com.galicianweather.domain.DataStatus
import com.galicianweather.domain.Error
import com.galicianweather.domain.Success
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
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
