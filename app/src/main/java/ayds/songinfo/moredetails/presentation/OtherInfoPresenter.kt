package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface OtherInfoPresenter {
    val artistBiographyObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)

}

internal class OtherInfoPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper
) : OtherInfoPresenter {

    override val artistBiographyObservable = Subject<ArtistBiographyUiState>()

    override fun getArtistInfo(artistName: String) {
        val artistBiography = repository.getArtistInfo(artistName)

        val uiState = artistBiography.toUiState(artistName)

        artistBiographyObservable.notify(uiState)
    }

    private fun ArtistBiography.toUiState(artName: String) = ArtistBiographyUiState(
        artName,
        artistBiographyDescriptionHelper.getDescription(this),
        articleUrl
    )
}