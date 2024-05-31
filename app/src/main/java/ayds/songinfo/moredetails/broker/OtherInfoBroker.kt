import ayds.songinfo.moredetails.domain.ArtistCard

//Broker sin proxys
class OtherInfoBroker(){
    fun getCards(artistName: String): List<ArtistCard>;
}

internal class OtherInfoBrokerImpl(
    val lastFmService : LastFMService,
    val wikipediaService : WikipediaTrackService,
    val nytService : NYTimesService,
){
    
    override fun getCards(artistName: String): List<ArtistCard>{
        cards: List<ArtistCard>;
        lastFmCard : ArtistCard = toCard(lastFmService.getArticle(artistName));
        cards.add(lastFmCard)
        wikipediaCard : ArtistCard = toCard(wikipediaService.getInfo(artistName));
        cards.add(wikipediaCard)
        nytCard : ArtistCard = toCard(nytService.getArtistInfo(artistName));
        cards.add(nytCard)
    }

    
}