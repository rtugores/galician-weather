package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.Callback
import huitca1212.galicianweather.domain.Result
import huitca1212.galicianweather.domain.UseCaseParams

class LastMinutesInfoRepository(private val dataSource: LastMinutesInfoNetworkDataSource) {

    fun getLastMinutesInfo(useCaseParams: UseCaseParams, listener: Callback<DataLastMinutesWrapper>) =
        dataSource.getLastMinutesInfo(useCaseParams, listener)
}