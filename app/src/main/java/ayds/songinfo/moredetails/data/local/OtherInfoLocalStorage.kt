package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.ArtistCard


interface OtherInfoLocalStorage {
    fun getArticle(artistName: String): ArtistCard?
    fun insertArtist(card: ArtistCard, artistName: String)
}

internal class OtherInfoLocalStorageImpl(
    private val articleDatabase: ArticleDatabase,
    private val localSource: String
) : OtherInfoLocalStorage {

    override fun getArticle(artistName: String): ArtistCard? {
        val artistEntity = articleDatabase.ArticleDao().getArticleByArtistName(artistName)
        return artistEntity?.let {
            ArtistCard(artistEntity.biography, artistEntity.articleUrl,localSource,"")
        }
    }

    override fun insertArtist(card: ArtistCard, artistName: String) {
        articleDatabase.ArticleDao().insertArticle(
            ArticleEntity(
                artistName, card.description, card.infoUrl
            )
        )
    }
}