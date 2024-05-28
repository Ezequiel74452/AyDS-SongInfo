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
object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter
    fun initGraph(context: Context) {
        LastFMInjector.init();

        val otherInfoService = LastFMInjector.lastFMService;//Broker?
        val cardDatabase =
            Room.databaseBuilder(context, CardDatabase::class.java, ARTICLE_BD_NAME).build()

        val articleLocalStorage = OtherInfoLocalStorageImpl(cardDatabase)

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, otherInfoService)

        val artistCardDescriptionHelper = ArtistCardDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistCardDescriptionHelper)
    }
}