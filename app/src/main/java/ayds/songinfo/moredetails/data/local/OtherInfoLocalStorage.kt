package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.ArtistBiography

interface OtherInfoLocalStorage {
    fun getArticle(artistName: String): ArtistBiography?
    fun insertArticle(artistBiography: ArtistBiography)
}

internal class OtherInfoLocalStorageImpl(
    private val articleDatabase: ArticleDatabase,
) : OtherInfoLocalStorage {

    override fun getArticle(artistName: String): ArtistBiography? {
        val artistEntity = articleDatabase.ArticleDao().getArticleByArtistName(artistName)
        return artistEntity?.let {
            ArtistBiography(artistName, artistEntity.biography, artistEntity.articleUrl)
        }
    }

    override fun insertArtist(artistInfo: ArtistBiography) {
        articleDatabase.ArticleDao().insertArticle(
            ArticleEntity(
                artistInfo.artistName, artistInfo.biography, artistInfo.articleUrl
            )
        )
    }
}