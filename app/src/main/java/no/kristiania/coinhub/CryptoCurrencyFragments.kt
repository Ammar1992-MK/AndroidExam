package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.CryptocurrencyLayoutBinding

class CryptoCurrencyFragments : Fragment(R.layout.cryptocurrency_layout) {

    private lateinit var binding : CryptocurrencyLayoutBinding

    companion object{

        fun newInstance() = CryptoCurrencyFragments()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = CryptocurrencyLayoutBinding.bind(view)

        var priceUsd = arguments?.getDouble("price")
        var name = arguments?.getString("name")
        var symbol = arguments?.getString("symbol")

        binding.currencyRate.text = priceUsd.toString()
        binding.currencyName.text = name.toString()
        binding.currencySymbol.text = symbol.toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImage)
    }
}