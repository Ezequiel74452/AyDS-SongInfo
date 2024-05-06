package ayds.songinfo.moredetails.domain

data class ArticleEntity{
    val artistName: String,
    val biography: String,
    val articleUrl: String
}

interface Repository{
    fun getArticle(artistName: String): ArticleEntity;
    fun insertArticle(article: ArticleEntity);
}