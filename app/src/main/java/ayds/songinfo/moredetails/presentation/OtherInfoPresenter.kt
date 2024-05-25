package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface OtherInfoPresenter {
    val artistCardObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)

}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistCardDescriptionHelper: ArtistCardDescriptionHelper
) : OtherInfoPresenter {

    override val artistCardObservable = Subject<ArtistBiographyUiState>()

    override fun getArtistInfo(artistName: String) {
        val artistCard = repository.getArtistInfo(artistName)

        val uiState = artistCard.toUiState(artistName)

        artistCardObservable.notify(uiState)
    }

    private fun ArtistCard.toUiState(artName: String) = ArtistBiographyUiState(
        artName,
        artistCardDescriptionHelper.getDescription(this,artName),
        infoUrl
    )
}