package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.BuyCurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.BuyCurrencyViewModel

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
        binding.currencyRateBuy.text = priceUsd.toString()

        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol?.toLowerCase()}@2x.png").into(binding.currencyImageBuy)

        //init viewModel
        viewModel.init(requireContext())

        viewModel.getReward()

        viewModel.getCurrencyVolume(symbol!!)

        viewModel.points.observe(viewLifecycleOwner){ points ->
            userPoints = points
            binding.USDBalance.text = "you have $userPoints USD"
        }

        binding.usdInput.addTextChangedListener {
            val input = it.toString()

            if(input.isEmpty()){
                binding.CurrencyOutput.text = " "
                binding.buyBtn.isEnabled = false
            } else {

                val output = input.toDouble() / priceUsd!!
                binding.CurrencyOutput.text = output.toString()
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

            if(USDInput > userPoints){
                Toast.makeText(requireContext(),"You don't have enough USD", Toast.LENGTH_LONG).show()
            }else {
                var newVolume = currentCurrencyVolume?.plus(binding.CurrencyOutput.text.toString().toDouble())
                if (isBought!!){
                    viewModel.updateCurrency(newVolume!!, symbol)
                } else {
                    viewModel.addTransaction(symbol!!, binding.CurrencyOutput.text.toString().toDouble(), "Bought", priceUsd!!)
                }
                viewModel.updateCurrency(userPoints - USDInput, "USD")
            }

        }

    }
}
//points!!.toDouble() - binding.usdInput.text.toString().toDouble()

//viewModel.updateUserPoints(userPoints - binding.usdInput.text.toString().toDouble())