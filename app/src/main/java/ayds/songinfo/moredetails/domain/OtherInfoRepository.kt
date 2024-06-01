package ayds.songinfo.moredetails.domain

interface OtherInfoRepository{
    fun getArtistCard(artistName: String): List<ArtistCard>
}