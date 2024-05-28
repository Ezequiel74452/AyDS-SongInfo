package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFMArticle
import ayds.artist.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.ArtistCard.CardSrc
import ayds.songinfo.moredetails.domain.OtherInfoRepository

/*TO DO: Usar broker en vez de clases de LastFM*/
internal class OtherInfoRepositoryImpl(
    private val localStorage: OtherInfoLocalStorage,
    private val externalService: LastFMService,// CAMBIAR CON BROKER?
) : OtherInfoRepository {

    override fun getArtistCard(artistName: String): ArtistCard {
        val dbCard = localStorage.getCard(artistName)
        val artistCard: ArtistCard

        if (dbCard != null) {
            artistCard = dbCard.apply{markItAsLocal()}
        } else {
            /*TO DO: reemplazar por proxy*/
                val lastFmArticle = externalService.getArticle(artistName)
                artistCard = lastFmArticle.toCard()
            /**/
            if (artistCard.description.isNotEmpty()) {
                localStorage.insertCard(artistCard)
            }
        }
        return artistCard;
    }

    private fun ArtistCard.markItAsLocal() {
        isLocallyStored = true
    }
    private fun LastFMArticle.toCard() =
        ArtistCard(artistName, biography, articleUrl, CardSrc.LASTFM)
}