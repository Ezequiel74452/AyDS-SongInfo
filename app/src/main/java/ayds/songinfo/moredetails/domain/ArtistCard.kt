package ayds.songinfo.moredetails.domain

data class ArtistCard (
        val artistName: String,
        val description: String,
        val infoUrl: String,
        val source: CardSrc,
        val sourceImg: String,
        var isLocallyStored: Boolean = false,
    )

data object EmptyCard{
    const val NAME :String = "Unknown Artist"
    val DESCRIPTION :String = "No description available"
    const val  URL :String = "No URL available"
}
enum class CardSrc { LASTFM, WIKIPEDIA, NYTIMES}