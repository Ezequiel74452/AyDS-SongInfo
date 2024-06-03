package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.R
import ayds.songinfo.moredetails.injector.OtherInfoInjector
import com.squareup.picasso.Picasso

class OtherInfoActivity : Activity() {
    private lateinit var cardContent1TextView: TextView
    private lateinit var openUrl1Button: Button
    private lateinit var sourceImage1View: ImageView

    private lateinit var cardContent2TextView: TextView
    private lateinit var openUrl2Button: Button
    private lateinit var sourceImage2View: ImageView

    private lateinit var cardContent3TextView: TextView
    private lateinit var openUrl3Button: Button
    private lateinit var sourceImage3View: ImageView

    private lateinit var presenter: OtherInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initViewProperties()
        initPresenter()

        observePresenter()
        getArtistCardsAsync()
    }

    private fun initPresenter() {
        OtherInfoInjector.initGraph(this)
        presenter = OtherInfoInjector.presenter
    }

    private fun observePresenter() {
        presenter.cardsObservable.subscribe { artistBiography ->
            updateUi(artistBiography)
        }
    }

    private fun initViewProperties() {
        cardContent1TextView = findViewById(R.id.cardContent1TextView)
        openUrl1Button = findViewById(R.id.openUrl1Button)
        sourceImage1View = findViewById(R.id.sourceImage1ImageView)

        cardContent2TextView = findViewById(R.id.cardContent2TextView)
        openUrl2Button = findViewById(R.id.openUrl2Button)
        sourceImage2View = findViewById(R.id.sourceImage2ImageView)

        cardContent3TextView = findViewById(R.id.cardContent3TextView)
        openUrl3Button = findViewById(R.id.openUrl3Button)
        sourceImage3View = findViewById(R.id.sourceImage3ImageView)
    }

    private fun getArtistCardsAsync() {
        Thread {
            getArtistCards()
        }.start()
    }

    private fun getArtistCards() {
        val artistName = getArtistName()
        presenter.updateCards(artistName)
    }

    private fun updateUi(uiStates: List<ArtistCardUiState>) {
        runOnUiThread {
            updateOpenUrlButton(openUrl1Button,uiStates[0].url)
            updateSourceLogo(sourceImage1View,uiStates[0].imageUrl)
            updateCardText(cardContent1TextView,uiStates[0].contentHtml)

            updateOpenUrlButton(openUrl2Button,uiStates[1].url)
            updateSourceLogo(sourceImage2View,uiStates[1].imageUrl)
            updateCardText(cardContent2TextView,uiStates[1].contentHtml)

            updateOpenUrlButton(openUrl3Button,uiStates[2].url)
            updateSourceLogo(sourceImage3View,uiStates[2].imageUrl)
            updateCardText(cardContent3TextView,uiStates[2].contentHtml)
        }
    }

    private fun updateOpenUrlButton(button: Button, url: String) {
        button.setOnClickListener {
            navigateToUrl(url)
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }
    private fun updateSourceLogo(imgview : ImageView, url: String) {
        Picasso.get().load(url).into(imgview)
    }
    private fun updateCardText(txtview: TextView, infoHtml: String) {
        txtview.text = Html.fromHtml(infoHtml)
    }

    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}

