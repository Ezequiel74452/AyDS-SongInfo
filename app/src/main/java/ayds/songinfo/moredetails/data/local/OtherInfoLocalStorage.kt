package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.CardSrc

interface OtherInfoLocalStorage {
    fun getCards(artistName: String): List<ArtistCard>
    fun insertCard(card: ArtistCard)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase
) : OtherInfoLocalStorage {

    override fun getCards(artistName: String): List<ArtistCard> {
        val artistEntities = cardDatabase.CardDao().getCardsByArtistName(artistName)
        val cards : MutableList<ArtistCard> = mutableListOf()
        artistEntities.forEach {
            cards.add(ArtistCard(it.artistName, it.content, it.url, CardSrc.entries[it.source],it.sourceImg))
        }
        return cards
    }

    override fun insertCard(card: ArtistCard) {
        cardDatabase.CardDao().insertCard(
            CardEntity(
                card.artistName, card.description, card.infoUrl, card.source.ordinal, card.sourceImg
            )
        )
    }
}