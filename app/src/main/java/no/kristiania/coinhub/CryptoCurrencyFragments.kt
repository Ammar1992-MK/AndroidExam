package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.CryptocurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.CryptoCurrencyViewModel

class CryptoCurrencyFragments : Fragment(R.layout.cryptocurrency_layout) {

    private lateinit var binding : CryptocurrencyLayoutBinding
    private val viewModel : CryptoCurrencyViewModel by viewModels()

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

        //init view model

        viewModel.init(requireContext())

        if (symbol != null) {
            viewModel.getCurrencyVolume(symbol)
        }


        viewModel.currencyVolumeLiveData.observe(viewLifecycleOwner){

            with(binding){

                if ( it == null){

                    wallet.text = "you have 0.00 ${symbol}"
                    sellNavBtn.isEnabled = false
                } else {

                    wallet.text = "you have ${it} ${symbol} "
                    sellNavBtn.isEnabled = true
                }
            }

        }


    }
}