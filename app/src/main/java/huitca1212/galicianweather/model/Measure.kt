package huitca1212.galicianweather.model

import com.google.gson.annotations.SerializedName


data class Measure(
    @SerializedName("codigoParametro") val parameterCode: String?,
    @SerializedName("nomeParametro") val parameterName: String?,
    @SerializedName("unidade") val units: String?,
    @SerializedName("valor") val value: Float?
)