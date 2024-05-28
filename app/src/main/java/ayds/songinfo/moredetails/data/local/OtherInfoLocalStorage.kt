package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.ArtistCard.CardSrc

interface OtherInfoLocalStorage {
    fun getCard(artistName: String): ArtistCard?
    fun insertCard(card: ArtistCard)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase
) : OtherInfoLocalStorage {

    override fun getCard(artistName: String): ArtistCard? {
        val artistEntity = cardDatabase.CardDao().getCardByArtistName(artistName)
        return artistEntity?.let {
            ArtistCard(artistName, artistEntity.content, artistEntity.url, CardSrc.entries[artistEntity.source] )
        }
    }

    override fun insertCard(card: ArtistCard) {
        cardDatabase.CardDao().insertCard(
            CardEntity(
                card.artistName, card.description, card.infoUrl, card.source.ordinal
            )
        )
    }
}