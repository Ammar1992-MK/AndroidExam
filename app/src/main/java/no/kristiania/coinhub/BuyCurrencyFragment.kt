package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.BuyCurrencyLayoutBinding

class BuyCurrencyFragment : Fragment(R.layout.buy_currency_layout) {

    private lateinit var binding : BuyCurrencyLayoutBinding

    companion object{
        fun newInstance() = BuyCurrencyFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BuyCurrencyLayoutBinding.bind(view)

        var priceUsd = arguments?.getDouble("priceUsd")
        var name = arguments?.getString("name")
        var symbol = arguments?.getString("symbol")

        binding.currencyNameBuy.text = name
        binding.currencySymbol.text = symbol
        binding.currencyRateBuy.text = priceUsd.toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImageBuy)

        binding.usdInput.addTextChangedListener(){
            var input = it.toString()

            if(input.isNullOrEmpty()){
                binding.CurrencyOutput.text = "${symbol}"
            } else {

                var output = input.toDouble() / priceUsd!!
                binding.CurrencyOutput.text = output.toString()
            }

        }
    }
}