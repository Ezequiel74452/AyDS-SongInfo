package ayds.external.lastfm.data

private const val LOGO_URL= "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
data class LastFMArticle(
    var artistName: String,
    var biography: String,
    var articleUrl: String,
    val logoUrl :String = LOGO_URL
)