package no.kristiania.coinhub.models

data class CurrencyStats (var Id: String,
                          var Symbol: String,
                          var Name: String,
                          var PriceUsd: Double,
                          var ChangePercent24Hr: Double) {
}