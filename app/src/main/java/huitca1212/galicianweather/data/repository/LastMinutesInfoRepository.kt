package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.LastMinutesInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataLastMinutesWrapper
import huitca1212.galicianweather.domain.Result
import huitca1212.galicianweather.domain.UseCaseParams

class LastMinutesInfoRepository(private val dataSource: LastMinutesInfoNetworkDataSource) {

    fun getLastMinutesInfo(useCaseParams: UseCaseParams, listener: ((Result<DataLastMinutesWrapper>) -> Unit)) =
        dataSource.getLastMinutesInfo(useCaseParams, listener)
}