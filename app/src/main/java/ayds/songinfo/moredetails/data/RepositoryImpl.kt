package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.domain.ArticleEntity
import ayds.songinfo.moredetails.domain.Repository


data class ArtistBiography(val artistName: String, val biography: String, val articleUrl: String)

private fun markItAsLocal(article: ArticleEntity) = ArticleEntity(article.artistName,"[**]" + article.biography,article.articleUrl)

                                            //???
class RepositoryImpl: Repository(private val db:ArticleDatabase, private val externalService: LastFMAPI){
    fun getArticle(artistName: String): ArticleEntity{
        val article? = db.ArticleDao().getArticleByArtistName(artistName);
        if(article == null){
            val artistBiography = getArticleFromService(artistName)
            if (artistBiography.biography.isNotEmpty()) {
                article = ArticleEntity(
                    artistBiography.artistName, artistBiography.biography, artistBiography.articleUrl
                )
                insertArticle(article);
            }
        }
        else{
            article = markItAsLocal(article);
        }
        return article;
    }

    fun insertArticle(article: ArticleEntity){
        db.insertArticle(article)
    }

    private fun getArticleFromService(artistName: String): ArtistBiography {
        var artistBiography = ArtistBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = getArtistBioFromExternalData(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

    private fun getArtistBioFromExternalData(
        serviceData: String?,
        artistName: String
    ): ArtistBiography {
       val gson = Gson()
        val jobj = gson.fromJson(serviceData, JsonObject::class.java)
    
        val artist = jobj["artist"].getAsJsonObject()
        val bio = artist["bio"].getAsJsonObject()
        val extract = bio["content"]
        val url = artist["url"]
        val text = extract?.asString ?: "No Results"
    
        return ArtistBiography(artistName, text, url.asString)
    }
}