package ayds.songinfo.moredetails.domain

data class ArtistCard(
    val description: String,
    val infoUrl: String,
    val source: String,
    val sourceLogoUrl: String
)

/*
data class ArtistBiography(
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    var isLocallyStored: Boolean = false
)*/