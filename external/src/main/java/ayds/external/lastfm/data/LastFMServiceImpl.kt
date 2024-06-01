package ayds.external.lastfm.data

import java.io.IOException

internal class LastFMServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMToArticleResolver: LastFMToArticleResolver
) : LastFMService {

    override fun getArticle(artistName: String): LastFMArticle? {

        var lastfmArticle : LastFMArticle? = LastFMArticle(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            lastfmArticle= lastFMToArticleResolver.map(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return lastfmArticle
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()
}