package no.kristiania.coinhub.repos

import no.kristiania.coinhub.datasources.DummySource
import no.kristiania.coinhub.datasources.LiveSource
import no.kristiania.coinhub.models.CurrencyStats

class CurrencyRepo {

    private val liveSource = LiveSource()

    suspend fun getCurrencySummary() : List<CurrencyStats> {

        return liveSource.getSummary()
    }
}