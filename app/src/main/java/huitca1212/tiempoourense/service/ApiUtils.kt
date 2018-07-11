package huitca1212.tiempoourense.service

object ApiUtils {

    private const val BASE_URL = "http://servizos.meteogalicia.gal/rss/observacion/"

    val dataService: DataService = RetrofitClient.getClient(BASE_URL).create(DataService::class.java)
}