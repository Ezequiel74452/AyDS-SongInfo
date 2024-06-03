package ayds.songinfo.moredetails.broker

import ayds.external.lastfm.injector.LastFMInjector
import ayds.songinfo.moredetails.domain.ArtistCard
import ayds.songinfo.moredetails.proxies.LastFMProxy
import ayds.songinfo.moredetails.proxies.NYTProxy
import ayds.songinfo.moredetails.proxies.WikipediaProxy
import ayds.external.newyorktimes.injector.NYTimesInjector
import ayds.external.wikipedia.injector.WikipediaInjector
import ayds.songinfo.moredetails.proxies.Proxy

class BrokerImpl: Broker {

    private lateinit var lastFMProxy: Proxy
    private lateinit var nytProxy: Proxy
    private lateinit var wikipediaProxy: Proxy

    init{
        initBroker()
    }

    override fun getArtistCards(artistName: String): List<ArtistCard> {
        val lastFMCard = lastFMProxy.getInfo(artistName)
        val nytCard = nytProxy.getInfo(artistName)
        val wikipediaCard = wikipediaProxy.getInfo(artistName)
        return listOf(lastFMCard, nytCard, wikipediaCard)
    }

    private fun initBroker() {
        lastFMProxy = LastFMProxy(LastFMInjector.lastFMService)
        nytProxy = NYTProxy(NYTimesInjector.nyTimesService)
        wikipediaProxy = WikipediaProxy(WikipediaInjector.wikipediaTrackService)
    }
}