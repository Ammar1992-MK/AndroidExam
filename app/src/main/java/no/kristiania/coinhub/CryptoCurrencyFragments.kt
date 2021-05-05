package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.CryptocurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.CryptoCurrencyViewModel
import java.math.RoundingMode

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
        var points = arguments?.get("points")

        binding.currencyRate.text = priceUsd.toString()
        binding.currencyName.text = name.toString()
        binding.currencySymbol.text = symbol.toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImage)


        binding.buyNavButton.setOnClickListener {
            var fragment = BuyCurrencyFragment.newInstance()
            fragment.arguments = Bundle().apply {
                putString("name", binding.currencyName.text.toString())
                putString("symbol", binding.currencySymbol.text.toString())
                putDouble("priceUsd", binding.currencyRate.text.toString().toDouble())
                putString("points", points.toString())
            }
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .addToBackStack("CryptoCurrency")
                .commit()
        }

        binding.sellNavBtn.setOnClickListener {
            var fragment = SellCurrencyFragment.newInstance()
            fragment.arguments = Bundle().apply {
                putString("name", binding.currencyName.text.toString())
                putString("symbol", binding.currencySymbol.text.toString())
                putDouble("priceUsd", binding.currencyRate.text.toString().toDouble())
                putString("points", points.toString())
            }
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .addToBackStack("CryptoCurrency")
                .commit()
        }

        //init view model
        if (symbol != null) {
            viewModel.init(requireContext(), symbol)
        }

        viewModel.currencyVolumeLiveData.observe(viewLifecycleOwner){
            with(binding){
                if ( it == null){
                    wallet.text = "you have 0.00 ${symbol}"
                    sellNavBtn.isEnabled = false
                } else {

                    wallet.text = "you have ${it.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()} ${symbol} "
                    sellNavBtn.isEnabled = true
                }
            }
        }

        viewModel.buyEnabled.observe(viewLifecycleOwner){
            binding.buyNavButton.isEnabled = it
        }

        viewModel.getEverything()

    }
}