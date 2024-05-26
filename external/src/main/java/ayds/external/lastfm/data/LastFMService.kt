package ayds.external.lastfm.data

interface LastFMService {
    fun getArticle(artistName: String): LastFMArticle
}