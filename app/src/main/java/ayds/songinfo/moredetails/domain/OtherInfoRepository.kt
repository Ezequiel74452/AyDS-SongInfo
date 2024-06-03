package ayds.songinfo.moredetails.domain

interface OtherInfoRepository{
    fun getArtistCards(artistName: String): List<ArtistCard>
}