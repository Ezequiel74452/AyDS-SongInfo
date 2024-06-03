package ayds.songinfo.moredetails.proxies

import ayds.external.lastfm.data.LastFMArticle
import ayds.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.CardSrc


class LastFMProxy(private val lastFMService: LastFMService): Proxy() {
    override fun getInfo(artistName: String): ArtistCard {
        val article = lastFMService.getArticle(artistName)
        return article?.toCard() ?: getEmptyCard(CardSrc.LASTFM)
    }

    private fun LastFMArticle.toCard() =
        ArtistCard(artistName, biography, articleUrl, CardSrc.LASTFM, logoUrl)
}