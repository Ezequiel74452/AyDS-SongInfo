package ayds.songinfo.moredetails.proxies

import ayds.external.wikipedia.data.WikipediaArticle
import ayds.external.wikipedia.data.WikipediaTrackService
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.CardSrc

class WikipediaProxy(private val wikipediaService: WikipediaTrackService): Proxy() {

    override fun getInfo(artistName: String): ArtistCard {
        return when(val article = wikipediaService.getInfo(artistName)) {
            is WikipediaArticle -> article.toCard(artistName)
            else -> getEmptyCard(CardSrc.WIKIPEDIA);
        }
    }

    private fun WikipediaArticle.toCard(artistName: String) =
        ArtistCard(artistName, description, wikipediaURL, CardSrc.WIKIPEDIA, wikipediaLogoURL )
}