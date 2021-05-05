package no.kristiania.coinhub.repos

import no.kristiania.coinhub.datasources.LiveSource
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.models.RateStats

class CurrencyRepo {

    private val liveSource = LiveSource()

     fun getCurrencySummary() : List<CurrencyStats> {

        return liveSource.getSummary()
    }

    fun getRecentRate(currencyName : String) : List<RateStats>{

        return liveSource.getRecentRate(currencyName)
    }
}