package com.galicianweather.data.datasource

import com.galicianweather.data.datasource.extensions.executeCall
import com.galicianweather.data.repository.GWNullBodyException
import com.galicianweather.network.StationApi
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class DailyInfoRemoteDataSource : KoinComponent {

    companion object {
        const val RAIN_PARAM = "PP_SUM_1.5m"
    }

    private val api by inject<StationApi>()

    fun getDailyInfo(idEst: String) =
        api.getDailyInfo(mapOf("idEst" to idEst))
            .executeCall() ?: throw GWNullBodyException()
}
