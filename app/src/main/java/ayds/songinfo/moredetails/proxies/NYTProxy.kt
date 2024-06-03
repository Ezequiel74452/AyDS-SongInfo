package ayds.songinfo.moredetails.proxies

import ayds.external.newyorktimes.data.NYTimesArticle
import ayds.external.newyorktimes.data.NYTimesService
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.ArtistCard.*
import ayds.songinfo.moredetails.domain.CardSrc


class NYTProxy(private val nytService: NYTimesService): Proxy() {
    override fun getInfo(artistName: String): ArtistCard {
        return when (val article = nytService.getArtistInfo(artistName)) {
            is NYTimesArticle.NYTimesArticleWithData -> article.toCard()
            is NYTimesArticle.EmptyArtistDataExternal -> getEmptyCard(CardSrc.NYTIMES)
        }
    }

    private fun NYTimesArticle.NYTimesArticleWithData.toCard(): ArtistCard {
        return ArtistCard(
            artistName = name ?: "Unknown Artist",
            description = info ?: "No description available",
            infoUrl = url,
            source = CardSrc.NYTIMES,
            logoUrl
        )
    }
}