package no.kristiania.coinhub.datasources

import no.kristiania.coinhub.models.CurrencyStats

class DummySource {

    fun getSummary() : List<CurrencyStats>{
        var list = ArrayList<CurrencyStats>()

        list.add(CurrencyStats("BTC", "Bitcoin", 55258.14, 0.09))
        list.add(CurrencyStats("ETH", "Ethereum", 55258.14, 0.09))
        list.add(CurrencyStats("BNB", "Binance", 55258.14, 0.09))
        list.add(CurrencyStats("XRP", "XRP", 55258.14, 0.09))
        list.add(CurrencyStats("USDT", "Tether", 55258.14, 0.09))
        list.add(CurrencyStats("ADA", "Cardano", 55258.14, 0.09))
        list.add(CurrencyStats("DOGE", "Dogecoin", 55258.14, 0.09))
        list.add(CurrencyStats("DOT", "Polkadot", 55258.14, 0.09))
        list.add(CurrencyStats("LTC", "Litecoin", 55258.14, 0.09))
        list.add(CurrencyStats("UNI", "Uniswap", 55258.14, 0.09))





        return list
    }
}