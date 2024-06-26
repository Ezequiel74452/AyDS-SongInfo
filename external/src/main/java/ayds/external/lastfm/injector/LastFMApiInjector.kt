package ayds.external.lastfm.injector

import ayds.external.lastfm.data.LastFMAPI
import ayds.external.lastfm.data.LastFMService
import ayds.external.lastfm.data.LastFMServiceImpl
import ayds.external.lastfm.data.LastFMToArticleResolver
import ayds.external.lastfm.data.LastFMToArticleResolverImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
object LastFMInjector {
    lateinit var lastFMToArticleResolver: LastFMToArticleResolver;
    lateinit var lastFMService: LastFMService;
    fun init() {
        lastFMToArticleResolver = LastFMToArticleResolverImpl()
        lastFMService = LastFMServiceImpl(getLastFMAPI(), lastFMToArticleResolver)
    }
    private fun getLastFMAPI(): LastFMAPI {
        val lastFMAPIRetrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return lastFMAPIRetrofit.create(LastFMAPI::class.java)
    }

}