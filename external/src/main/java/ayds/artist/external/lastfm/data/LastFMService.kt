package ayds.songinfo.moredetails.data.external

interface LastFMService {
    fun getArticle(artistName: String): DomainArticle
}