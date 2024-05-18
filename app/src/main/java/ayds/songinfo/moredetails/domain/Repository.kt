package ayds.songinfo.moredetails.domain

interface Repository{
    fun getArtistInfo(artistName: String): ArtistBiography;
}