package ayds.external.lastfm.data

import com.google.gson.Gson
import com.google.gson.JsonObject

interface LastFMToArticleResolver {
    fun map(serviceData: String?, artistName: String): LastFMArticle?
}

private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"
private const val NO_RESULTS = "No Results"

internal class LastFMToArticleResolverImpl : LastFMToArticleResolver {
    override fun map(
        serviceData: String?,
        artistName: String
    ): LastFMArticle? {
        val gson = Gson()

        val jasonObject = gson.fromJson(serviceData, JsonObject::class.java)

        val artist = jasonObject[ARTIST].getAsJsonObject()
        val bio = artist[BIO].getAsJsonObject()
        val extract = bio[CONTENT]
        val url = artist[URL]
        val text = extract?.asString ?: NO_RESULTS

        return if (text == NO_RESULTS) null
               else LastFMArticle(artistName, text, url.asString)
    }
}