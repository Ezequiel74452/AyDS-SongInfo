

class Logic(private val repository: Repository){
    fun getArtistInfo(name: String){
        ArticleEntity article = repository.getArticle(name);
    }
}

