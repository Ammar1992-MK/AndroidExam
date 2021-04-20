package no.kristiania.coinhub.datasources

import no.kristiania.coinhub.models.CurrencyStats

class DummySource {

    fun getSummary() : List<CurrencyStats>{
        var list = ArrayList<CurrencyStats>()

        list.add(CurrencyStats("bitcoin", "BTC", "Bitcoin", 55258.147, 0.09))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))
        list.add(CurrencyStats("ethereum", "ETH", "Ethereum", 2198.553, 3.04 ))



        return list
    }
}