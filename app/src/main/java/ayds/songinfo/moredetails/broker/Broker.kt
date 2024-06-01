package ayds.songinfo.moredetails.broker

import ayds.songinfo.moredetails.domain.ArtistCard

interface Broker {
    fun getArtistCards(artistName: String): List<ArtistCard>
}