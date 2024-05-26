package ayds.external.wikipedia.injector


import ayds.external.wikipedia.data.WikipediaTrackService
import ayds.external.wikipedia.data.JsonToInfoResolver
import ayds.external.wikipedia.data.WikipediaToInfoResolver
import ayds.external.wikipedia.data.WikipediaTrackAPI
import ayds.external.wikipedia.data.WikipediaTrackServiceImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val WIKIPEDIA_URL = "https://en.wikipedia.org/w/"

object WikipediaInjector {
    private val wikipediaTrackAPI = getWikipediaAPI()
    private val wikipediaToInfoResolver: WikipediaToInfoResolver = JsonToInfoResolver()

    val wikipediaTrackService: WikipediaTrackService =
        WikipediaTrackServiceImpl(
            wikipediaTrackAPI,
            wikipediaToInfoResolver
        )

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(WIKIPEDIA_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private fun getWikipediaAPI() = getRetrofit().create(WikipediaTrackAPI::class.java)
}