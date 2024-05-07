package ayds.songinfo.moredetails.view

import androidx.room.Room.databaseBuilder

object Inyector{
    private lateinit var repository : Repository;

    fun initRepository(){
        val localDatabase = buildArticleDatabase();
        val externalService = createLastFMAPI();
        repositorio = RepositoryImpl(localDatabase,externalService);
    }

    private fun buildArticleDatabase() : ArticleData{
        return
            databaseBuilder(this, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()
    }

    private fun createLastFMAPI() : LastFMAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        return retrofit.create(LastFMAPI::class.java)
    }
}
