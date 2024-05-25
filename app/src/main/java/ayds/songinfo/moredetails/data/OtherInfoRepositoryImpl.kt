package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFMArticle
import ayds.artist.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.OtherInfoRepository

/*TO DO: Usar broker en vez de clases de LastFM*/
internal class OtherInfoRepositoryImpl(
    private val localStorage: OtherInfoLocalStorage,
    private val externalService: LastFMService,// CAMBIAR CON BROKER?
) : OtherInfoRepository {

    override fun getArtistInfo(artistName: String): ArtistCard {
        val dbCard = localStorage.getArticle(artistName)
        val artistCard: ArtistCard

        if (dbCard != null) {
            //Obsoleto, Card tiene prop source
            //artistBio = dbArticle.apply { markItAsLocal() }
            artistCard = dbCard;
        } else {
            /*TO DO: reemplazar por proxy*/
                val lastFmArticle = externalService.getArticle(artistName)
                artistCard = mapToArtistCard(lastFmArticle)
            /**/
            if (artistCard.description.isNotEmpty()) {
                localStorage.insertArtist(artistCard, artistName)
            }
        }
        return artistCard;
    }

    private fun mapToArtistCard(lfmArticle: LastFMArticle): ArtistCard{
        return ArtistCard(
            lfmArticle.biography,lfmArticle.articleUrl,"LastFM",lfmArticle.lastFMLogoUrl
        );
    }
}