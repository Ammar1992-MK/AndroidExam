package no.kristiania.coinhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.CryptocurrencyLayoutBinding

class CryptoCurrencyActivity : AppCompatActivity() {

    private lateinit var binding : CryptocurrencyLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CryptocurrencyLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val name = intent.getStringExtra("NAME")
        val symbol = intent.getStringExtra("SYMBOL")
        val priceUsd = intent.getDoubleExtra("PRICEUSD", 0.0)

        binding.currencyRate.text = priceUsd.toString()
        binding.currencyName.text = name.toString()
        binding.currencySymbol.text = symbol.toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImage)
    }
}