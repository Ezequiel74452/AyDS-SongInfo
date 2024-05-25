package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFMArticle
import ayds.artist.external.lastfm.data.LastFMService
import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

/*TO DO: Usar broker en vez de clases de LastFM*/
internal class OtherInfoRepositoryImpl(
    private val localStorage: OtherInfoLocalStorage,
    private val externalService: LastFMService,
) : OtherInfoRepository {

    override fun getArtistInfo(artistName: String): ArtistBiography {
        val dbArticle = localStorage.getArticle(artistName)

        val artistBio: ArtistBiography

        if (dbArticle != null) {
            artistBio = dbArticle.apply { markItAsLocal() }
        } else {
            /*TO DO: reemplazar por proxy*/
            artistBio = mapToArtistBiography(externalService.getArticle(artistName))
            if (artistBio.biography.isNotEmpty()) {
                localStorage.insertArtist( artistBio )
            }
        }
        return artistBio;
    }

    private fun mapToArtistBiography(lfmArticle: LastFMArticle): ArtistBiography{
        return ArtistBiography(
            lfmArticle.artistName,lfmArticle.biography,lfmArticle.articleUrl,false
        );
    }
    private fun ArtistBiography.markItAsLocal() {
        isLocallyStored = true
    }
}