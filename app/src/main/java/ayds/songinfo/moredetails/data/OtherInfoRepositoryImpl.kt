package ayds.songinfo.moredetails.data

//import ayds.songinfo.moredetails.data.external.OtherInfoService
import
import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

internal class OtherInfoRepositoryImpl(
    private val localStorage: OtherInfoLocalStorage,
    private val externalService: OtherInfoService,
) : OtherInfoRepository {

    override fun getArtistInfo(artistName: String): ArtistBiography {
        val dbArticle = localStorage.getArticle(artistName)

        val artistBio: ArtistBiography

        if (dbArticle != null) {
            artistBio = dbArticle.apply { markItAsLocal() }
        } else {
            artistBio = externalService.getArticle(artistName)
            if (artistBio.biography.isNotEmpty()) {
                localStorage.insertArtist( artistBio )
            }
        }
        return artistBio;
    }

    private fun ArtistBiography.markItAsLocal() {
        isLocallyStored = true
    }
}