package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.SellCurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.SellCurrencyViewModel
import java.math.RoundingMode

class SellCurrencyFragment : Fragment(R.layout.sell_currency_layout) {

    private lateinit var binding: SellCurrencyLayoutBinding
    private val viewModel = SellCurrencyViewModel()
    var currencyVolume: Double = 0.0
    var soldCurrencyVolume: Double = 0.0
    var newCurrencyVolume: Double = 0.0
    var USDVolume: Double = 0.0

    companion object {

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
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png")
            .into(binding.currencyImageSell)


        //init view model
        viewModel.init(requireContext())

        //fetching the currency volume
        viewModel.getCurrencyVolume(symbol!!)

        viewModel.currencyVolume.observe(viewLifecycleOwner) {
            currencyVolume = it
            binding.currencyVolume.text = "You have $currencyVolume $symbol"
        }

        binding.currencyInputSell.addTextChangedListener {
            val input = it.toString()

            if (input.isEmpty()) {
                binding.USDOutput.text = " "
                binding.sellBtn.isEnabled = false
            } else {

                val output = input.toDouble() * priceUsd!!
                binding.USDOutput.text = output.toString()
                binding.sellBtn.isEnabled = true
            }
        }

        viewModel.USDpoints.observe(viewLifecycleOwner) {
            USDVolume = it
        }

        //updating the database
        binding.sellBtn.setOnClickListener {
            val currencyInput = binding.currencyInputSell.text.toString().toDouble()

            if(currencyInput > currencyVolume){
                Toast.makeText(requireContext(),"Amount $symbol is not sufficient", Toast.LENGTH_LONG).show()

            }else {
                soldCurrencyVolume = currencyInput
                newCurrencyVolume = currencyVolume - soldCurrencyVolume
                // var newVolume = binding.currencyVolume.text.toString().toDouble() - binding.currencyInputSell.toString().toDouble()
                viewModel.updateCurrency(newCurrencyVolume, symbol)

                var currentUSDVolume = binding.USDOutput.text.toString().toDouble()

                val newUSDVolume = USDVolume + currentUSDVolume
                val usdVolume = newUSDVolume.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

                viewModel.updateCurrency(usdVolume, "USD")
                viewModel.saveTransactionHistory(soldCurrencyVolume,symbol,currentUSDVolume )
            }

        }
    }
}

