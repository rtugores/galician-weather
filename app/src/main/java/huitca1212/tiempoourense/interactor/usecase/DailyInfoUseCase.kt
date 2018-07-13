package huitca1212.tiempoourense.interactor.usecase

import huitca1212.tiempoourense.interactor.DataPolicy
import huitca1212.tiempoourense.data.datasource.DailyInfoDataSource
import huitca1212.tiempoourense.data.repository.DailyInfoRepository
import kotlinx.coroutines.experimental.async


class DailyInfoUseCase(
    private val repository: DailyInfoRepository = DailyInfoRepository(DailyInfoDataSource())
) {

    fun execute(stationId: String) = async { repository.getDailyInfo(DataPolicy.NETWORK, stationId) }
}