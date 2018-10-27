package huitca1212.galicianweather.data.repository

import huitca1212.galicianweather.data.datasource.DailyInfoNetworkDataSource
import huitca1212.galicianweather.data.datasource.model.DataDailyWrapper
import huitca1212.galicianweather.domain.Result
import huitca1212.galicianweather.domain.UseCaseParams

class DailyInfoRepository(private val dataSource: DailyInfoNetworkDataSource) {

    fun getDailyInfo(useCaseParams: UseCaseParams, listener: ((Result<DataDailyWrapper>) -> Unit)) =
        dataSource.getDailyInfo(useCaseParams, listener)
}