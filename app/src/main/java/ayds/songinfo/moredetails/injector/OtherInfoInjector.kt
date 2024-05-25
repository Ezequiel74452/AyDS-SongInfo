package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastfm.injector.LastFMInjector
import ayds.songinfo.moredetails.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.presentation.ArtistCardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.presentation.OtherInfoPresenterImpl

private const val ARTICLE_BD_NAME = "database-article"
private const val LOCAL_SOURCE = "localStorage"
object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter
    fun initGraph(context: Context) {
        val otherInfoService = LastFMInjector.lastFMService;//Broker?
        val articleDatabase =
            Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()

        val articleLocalStorage = OtherInfoLocalStorageImpl(articleDatabase,LOCAL_SOURCE)

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, otherInfoService)

        val artistCardDescriptionHelper = ArtistCardDescriptionHelperImpl(LOCAL_SOURCE)

        presenter = OtherInfoPresenterImpl(repository, artistCardDescriptionHelper)
    }
}