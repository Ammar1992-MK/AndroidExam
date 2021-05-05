package no.kristiania.coinhub

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.BuyCurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.BuyCurrencyViewModel
import java.math.RoundingMode

class BuyCurrencyFragment : Fragment(R.layout.buy_currency_layout) {

    private lateinit var binding : BuyCurrencyLayoutBinding
    private val viewModel = BuyCurrencyViewModel()
    private var userPoints : Double = 0.0
    private var isBought : Boolean? = null
    private var currentCurrencyVolume : Double? = null

    companion object{
        fun newInstance() = BuyCurrencyFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BuyCurrencyLayoutBinding.bind(view)

        val priceUsd = arguments?.getDouble("priceUsd")
        val name = arguments?.getString("name")
        val symbol = arguments?.getString("symbol")

        binding.currencyNameBuy.text = name
        binding.currencySymbol.text = symbol
        binding.currencyRateBuy.text = "$${priceUsd.toString()}"

        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImageBuy)

        //init viewModel
        viewModel.init(requireContext())

        viewModel.getReward()

        viewModel.getCurrencyVolume(symbol!!)

        viewModel.points.observe(viewLifecycleOwner){ points ->
            userPoints = points
            binding.USDBalance.text = "You have $userPoints USD."
        }

        binding.usdInput.addTextChangedListener {
            val input = it.toString()

            if(input.isEmpty()){
                binding.CurrencyOutput.text = " "
                binding.buyBtn.isEnabled = false
            } else {

                val output = input.toDouble() / priceUsd!!
                Log.d("output", output.toString())
                binding.CurrencyOutput.text = output.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
                binding.buyBtn.isEnabled = true
            }
        }

        viewModel.currencyVolume.observe(viewLifecycleOwner){
            if(it !== null){
                currentCurrencyVolume = it
                isBought = true
            } else {
                isBought = false
            }
        }

        binding.buyBtn.setOnClickListener {
            val USDInput = binding.usdInput.text.toString().toDouble()
            val currencyOutput = binding.CurrencyOutput.text.toString().toDouble()
            val  currencyVolume = currencyOutput.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

            if(USDInput > userPoints){
                Toast.makeText(requireContext(),"You don't have enough USD", Toast.LENGTH_LONG).show()
            }else {
                var newVolume = currentCurrencyVolume?.plus(currencyVolume)
                if (isBought!!){
                    viewModel.updateCurrency(newVolume!!, symbol)
                    viewModel.saveTransactionHistory(currencyVolume, symbol, USDInput)
                } else {
                    viewModel.addTransaction(symbol!!, currencyVolume, "Bought", priceUsd!!)
                    viewModel.saveTransactionHistory(currencyVolume, symbol, USDInput)
                }
                viewModel.updateCurrency(userPoints - USDInput, "USD")

                //navigateToScreen4()
            }

        }

    }

    fun navigateToScreen4(){

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment , CryptoCurrencyFragments.newInstance())
            .commit()
    }
}
