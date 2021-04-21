package no.kristiania.coinhub.datasources

import no.kristiania.coinhub.models.CurrencyStats
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class LiveSource {

    fun getSummary() : List<CurrencyStats> {
        val list = ArrayList<CurrencyStats>()


        var response = get("https://api.coincap.io/v2/assets")

        if(response.isSuccessful){
            var currencies = JSONObject(response.body).getJSONArray("data")

            for (i in 0 until currencies.length()){

                list.add(
                    CurrencyStats(

                        currencies.getJSONObject(i).getString("symbol"),
                        currencies.getJSONObject(i).getString("name"),
                        currencies.getJSONObject(i).getDouble("priceUsd"),
                        currencies.getJSONObject(i).getDouble("changePercent24Hr"),
                )
                )
            }
        }


        return  list

    }


    private fun get(endpointURL: String): HTTPResponse {

        val url = URL(endpointURL)
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"

            connection.connect()

            val stream = if (connection.responseCode in 200..300) connection.inputStream else connection.errorStream

            val responseBody = try {
                stream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            } catch (e: Throwable) {
                ""
            }

            return HTTPResponse(connection.responseCode, responseBody)
        } catch (e: Throwable) {
            return HTTPResponse(connection.responseCode, "")
        } finally {
            connection.disconnect()
        }
    }


    class HTTPResponse (private val statusCode: Int, val body : String) {
        val isSuccessful = statusCode in 200..300
    }
}
