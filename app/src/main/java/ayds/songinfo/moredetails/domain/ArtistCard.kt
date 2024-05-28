package ayds.songinfo.moredetails.domain

data class ArtistCard(
    val artistName: String,
    val description: String,
    val infoUrl: String,
    val source: CardSrc,
    var isLocallyStored: Boolean = false
)

enum class CardSrc{
    LASTFM
}
