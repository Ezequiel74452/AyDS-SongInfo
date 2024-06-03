package ayds.songinfo.moredetails.proxies

import ayds.songinfo.moredetails.domain.CardSrc
import ayds.songinfo.moredetails.domain.EmptyCard
import ayds.songinfo.moredetails.domain.ArtistCard

abstract class Proxy {
    abstract fun getInfo(artistName: String): ArtistCard
    fun getEmptyCard(src: CardSrc) : ArtistCard{
        return ArtistCard(
            EmptyCard.NAME,
            EmptyCard.DESCRIPTION,
            EmptyCard.URL,
            src,
            ""
        )
    }
}