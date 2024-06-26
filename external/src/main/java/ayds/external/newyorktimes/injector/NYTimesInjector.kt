package ayds.external.newyorktimes.injector

import ayds.external.newyorktimes.data.NYTimesAPI
import ayds.external.newyorktimes.data.NYTimesService
import ayds.external.newyorktimes.data.NYTimesServiceImpl
import ayds.external.newyorktimes.data.NYTimesToArtistResolver
import ayds.external.newyorktimes.data.NYTimesToArtistResolverImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LINK_API_NYTIMES = "https://api.nytimes.com/svc/search/v2/"

object NYTimesInjector {
    private val nyTimesAPI = getNYTimesAPI()
    private val nyTimesToArtistResolver: NYTimesToArtistResolver = NYTimesToArtistResolverImpl()

    val nyTimesService: NYTimesService = NYTimesServiceImpl(nyTimesAPI, nyTimesToArtistResolver)

    private fun getNYTimesAPI(): NYTimesAPI {
        val nyTimesAPIRetrofit = Retrofit.Builder()
            .baseUrl(LINK_API_NYTIMES)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return nyTimesAPIRetrofit.create(NYTimesAPI::class.java)
    }
}