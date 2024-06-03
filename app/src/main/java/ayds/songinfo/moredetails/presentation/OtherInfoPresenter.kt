package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.CardSrc
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface OtherInfoPresenter {
    val cardsObservable: Observable<List<ArtistCardUiState>>
    fun updateCards(artistName: String)

}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistCardDescriptionHelper: ArtistCardDescriptionHelper
) : OtherInfoPresenter {

    override val cardsObservable = Subject<List<ArtistCardUiState>>()

    override fun updateCards(artistName: String) {
        val artistCards = repository.getArtistCards(artistName)
        val uiStates : MutableList<ArtistCardUiState> = mutableListOf()

        artistCards.forEach{
            uiStates.add(it.toUiState())
        }
        cardsObservable.notify(uiStates)
    }

    private fun ArtistCard.toUiState() = ArtistCardUiState(
        artistName,
        artistCardDescriptionHelper.getDescription(this),
        infoUrl,
        sourceImg
    )
}