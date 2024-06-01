package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import ayds.songinfo.moredetails.broker.Broker
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.EmptyCard

/*TO DO: Usar broker en vez de clases de LastFM*/
internal class OtherInfoRepositoryImpl(
    private val localStorage: OtherInfoLocalStorage,
    private val broker: Broker,
) : OtherInfoRepository {

    override fun getArtistCard(artistName: String): List<ArtistCard>{
        val dbCards = localStorage.getCards(artistName)
        var cards: MutableList<ArtistCard> = mutableListOf()

        //Si falta alguna tarjeta recupera todas
        if (dbCards.size == 3) {
            dbCards.forEach{
                cards.add(it.apply{markItAsLocal()})
            }
        } else {
            cards = broker.getArtistCards(artistName).toMutableList()
            cards.forEach{
                if(it.description != EmptyCard.DESCRIPTION){
                    localStorage.insertCard(it)
                }
            }
        }
        return cards
    }

    private fun ArtistCard.markItAsLocal() {
        isLocallyStored = true
    }
}