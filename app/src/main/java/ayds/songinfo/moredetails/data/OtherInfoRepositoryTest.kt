package ayds.songinfo.moredetails.data

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OtherInfoRepositoryTest{
    private val localStorage : OtherInfoLocalStorage = mockk(relaxUnitFun=true)
    private val externalService : OtherInfoService = mockk(relaxUnitFun=true)
    private val repository : Repository = RepositoryImpl(localStorage,externalService);

    //tests:
    //*encuentra la cancion en la db, la marca como local y la retorna
    //no encuentra la cancion en la db, consulta el servicio externo y la guarda
    //no encuentra la cancion ni en la db ni en el servicio externo

    @Test
    fun `given existing song by name should return the song and mark it as local`() {
        val artistBio : ArtistBiography = mockk()
        every { localStorage.getArticle("name") } returns artistBio

        val result = repository.getArtistInfo("name")

        assertEquals(artistBio, result);
        assertTrue(artistBio.isLocallyStored);
    }

    @test
    fun `given non existing song by name should get it from external service and store it if its biography exists`(){
        val artistBio : ArtistBiography = ArtistBiography("name","bio","url",false);
        every{ localStorange.getArticle("name")} returns null
        every{ externalService.getArticle("name")} returns artistBio

        val result = repository.getArtistInfo("name");

        assertEquals(result, artistBio);
        verify{localStorage.insertArtist(artistBio)}
    }
}