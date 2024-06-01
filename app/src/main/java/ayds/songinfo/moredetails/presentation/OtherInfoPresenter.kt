package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface OtherInfoPresenter {
    val cardObservable: Observable<ArtistCardUiState>
    fun updateCard(artistName: String)

}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistCardDescriptionHelper: ArtistCardDescriptionHelper
) : OtherInfoPresenter {

    override val cardObservable = Subject<ArtistCardUiState>()

    override fun updateCard(artistName: String) {
        val artistCard = repository.getArtistCard(artistName)

        val uiState = artistCard.toUiState()

        cardObservable.notify(uiState)
    }

    private fun ArtistCard.toUiState() = ArtistCardUiState(
        artistName,
        artistCardDescriptionHelper.getDescription(this),
        infoUrl
    )
}