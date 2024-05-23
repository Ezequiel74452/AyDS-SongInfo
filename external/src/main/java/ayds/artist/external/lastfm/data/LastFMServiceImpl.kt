package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.DomainArticle
import java.io.IOException

internal class LastFMServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver
) : LastFMService {

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