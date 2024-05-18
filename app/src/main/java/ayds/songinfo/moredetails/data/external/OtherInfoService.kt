package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.DomainArticle
import java.io.IOException

interface OtherInfoService {
    fun getArticle(artistName: String): DomainArticle
}
internal class OtherInfoServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver
) : OtherInfoService {

    override fun getArticle(artistName: String): DomainArticle {

        var domArticle = DomainArticle(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            domArticle= lastFMToDomainArticleResolver.map(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return domArticle;
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}