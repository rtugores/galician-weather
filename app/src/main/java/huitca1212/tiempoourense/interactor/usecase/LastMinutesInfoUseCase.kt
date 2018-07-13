package huitca1212.tiempoourense.interactor.usecase

import huitca1212.tiempoourense.data.datasource.LastMinutesInfoDataSource
import huitca1212.tiempoourense.data.repository.LastMinutesInfoRepository
import huitca1212.tiempoourense.interactor.DataPolicy
import kotlinx.coroutines.experimental.async


class LastMinutesInfoUseCase(
    private val repository: LastMinutesInfoRepository = LastMinutesInfoRepository(LastMinutesInfoDataSource())
) {

    fun execute(stationId: String) = async { repository.getLastMinutesInfo(DataPolicy.NETWORK, stationId) }
}
