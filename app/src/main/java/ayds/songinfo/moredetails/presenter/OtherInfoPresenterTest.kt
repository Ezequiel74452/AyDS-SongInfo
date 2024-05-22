class OtherInfoPresenterTest{
    private val 

    fun ´get artistInfo should return artistBiography iu state´(){
        ArtistBiography = artistBiography("name","bio","url")
        every{OtherInfoRepository.getArtistInfo("name")} return artistBiography;
        every{artistBiographyDescriptionHelper.getDescription(artistBiography)}
        val 
    }
}
