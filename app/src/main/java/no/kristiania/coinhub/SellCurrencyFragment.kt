package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.SellCurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.SellCurrencyViewModel

class SellCurrencyFragment : Fragment(R.layout.sell_currency_layout) {

    private lateinit var binding : SellCurrencyLayoutBinding
    private val viewModel = SellCurrencyViewModel()

    companion object{

        fun newInstance() = SellCurrencyFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SellCurrencyLayoutBinding.bind(view)

        val priceUsd = arguments?.getDouble("priceUsd")
        val name = arguments?.getString("name")
        val symbol = arguments?.getString("symbol")

        binding.currencySymbolSell.text = symbol
        binding.currencyNameSell.text = name
        binding.currencyRateSell.text = priceUsd.toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImageSell)

        //init view model
        viewModel.init(requireContext())

        //fetching the currency volume
        viewModel.getCurrencyVolume(symbol!!)
    }




}