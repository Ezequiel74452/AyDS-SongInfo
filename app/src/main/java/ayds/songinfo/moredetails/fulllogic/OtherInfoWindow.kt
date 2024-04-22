package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

class OtherInfoWindow : Activity() {
    private var textPane1: TextView? = null
    private var dataBase: ArticleDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updatePanel()
        openDatabase()
        displayArtistInfo(intent.getStringExtra(ARTIST_NAME_EXTRA))
    }

    private fun updatePanel() {
        setContentView(R.layout.activity_other_info)
        textPane1 = findViewById(R.id.textPane1)
    }

    private fun openDatabase() {
        dataBase = databaseBuilder(this, ArticleDatabase::class.java, DATABASE_NAME).build()
        testDatabase()
    }

    private fun testDatabase() {
        Thread {
            dataBase!!.ArticleDao().insertArticle(ArticleEntity("test", "sarasa", EMPTY_STR))
            Log.e(LOG_TAG, EMPTY_STR + dataBase!!.ArticleDao().getArticleByArtistName("test"))
            Log.e(LOG_TAG, EMPTY_STR + dataBase!!.ArticleDao().getArticleByArtistName("nada"))
        }.start()
    }

    private fun displayArtistInfo(artistName: String?) {
        Log.e(LOG_TAG, "$ARTIST_NAME_EXTRA $artistName")

        Thread {
            val article = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            val textBio = if (article == null) {
                getArticleFromService(artistName)
            } else {
                articleInDB(article)
            }

            loadImage(textBio)
        }.start()
    }

    private fun getApi(): LastFMAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(RETROFIT_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return retrofit.create(LastFMAPI::class.java)
    }

    private fun getArticleFromService(artistName: String): String {
        val callResponse: Response<String>
        var returnText = EMPTY_STR
        val lastFMAPI = getApi()
        try {
            callResponse = lastFMAPI.getArtistInfo(artistName).execute()

            Log.e(LOG_TAG, "JSON " + callResponse.body())

            val jobj = Gson().fromJson(callResponse.body(), JsonObject::class.java)
            val artist = jobj[ARTIST].getAsJsonObject()
            val bioContent = artist[BIO].getAsJsonObject()[CONTENT]
            val url = artist[URL]
            if (bioContent == null) {
                returnText = "No Results"
            } else {
                returnText = bioContent.asString.replace("\\n", "\n")
                returnText = textToHtml(returnText, artistName)
                saveArticleToDB(artistName, returnText, url.asString)
            }
            openURL(url.asString)
        } catch (e1: IOException) {
            Log.e(LOG_TAG, "Error $e1")
            e1.printStackTrace()
        }
        return  returnText
    }

    private fun saveArticleToDB(artistName: String, bio: String, url: String) {
        Thread {
            dataBase!!.ArticleDao().insertArticle(
                ArticleEntity(
                    artistName, bio, url
                )
            )
        }
            .start()
    }

    private fun openURL(url: String) {
        findViewById<View>(R.id.openUrlButton1).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun articleInDB(article: ArticleEntity): String {
        val returnText = "[*]" + article.biography
        val urlString = article.articleUrl
        openURL(urlString)
        return returnText
    }

    private fun loadImage(text: String) {
        runOnUiThread {
            Picasso.get().load(LASTFM_LOGO_URL).into(findViewById<View>(R.id.imageView1) as ImageView)
            textPane1!!.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"
        const val LASTFM_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
        const val LOG_TAG = "TAG"
        const val DATABASE_NAME = "database-name-thename"
        const val EMPTY_STR = ""
        const val ARTIST = "artist"
        const val BIO = "bio"
        const val CONTENT = "content"
        const val URL = "url"
        fun textToHtml(text: String, term: String?): String {
            val builder = StringBuilder()
            builder.append("<html><div width=400>")
            builder.append("<font face=\"arial\">")
            val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace(
                    "(?i)$term".toRegex(),
                    "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
                )
            builder.append(textWithBold)
            builder.append("</font></div></html>")
            return builder.toString()
        }
    }
}
