package ayds.artist.external.lastfm.injector

import ayds.artist.external.lastfm.data.LastFMAPI
import ayds.artist.external.lastfm.data.LastFMService
import ayds.artist.external.lastfm.data.LastFMServiceImpl
import ayds.artist.external.lastfm.data.LastFMToArtistBiographyResolver
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
object NYTimesInjector {
    private val lastFMAPI = getLastFMAPI()
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver = LastFMToArtistBiographyResolverImpl()

    val lastFMService: LastFMService = LastFMServiceImpl(lastFMAPI, lastFMToDomainArticleResolver)

    private fun getLastFMAPI(): LastFMAPI {
        val lastFMAPIRetrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return lastFMAPIRetrofit.create(LastFMAPI::class.java)
    }
}