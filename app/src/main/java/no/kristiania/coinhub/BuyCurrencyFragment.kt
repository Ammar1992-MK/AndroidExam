package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import no.kristiania.coinhub.databinding.BuyCurrencyLayoutBinding
import no.kristiania.coinhub.viewmodels.BuyCurrencyViewModel
import kotlin.properties.Delegates

class BuyCurrencyFragment : Fragment(R.layout.buy_currency_layout) {

    private lateinit var binding : BuyCurrencyLayoutBinding
    private val viewModel = BuyCurrencyViewModel()
    private var userPoints : Double = 0.0

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

        //init viewModel
        viewModel.init(requireContext())

        viewModel.getReward()

        viewModel.points.observe(viewLifecycleOwner){ points ->
            userPoints = points
            binding.USDBalance.text = "you have ${userPoints} USD"
        }

        binding.usdInput.addTextChangedListener(){
            var input = it.toString()

            if(input.isNullOrEmpty()){
                binding.CurrencyOutput.text = " "
                binding.buyBtn.isEnabled = false
            } else {

                var output = input.toDouble() / priceUsd!!
                binding.CurrencyOutput.text = output.toString()
                binding.buyBtn.isEnabled = true
            }
        }

        binding.buyBtn.setOnClickListener {
            viewModel.addTransaction(symbol!!, binding.CurrencyOutput.text.toString().toDouble(), "buy", priceUsd!!)
            viewModel.updateUserPoints(userPoints - binding.usdInput.text.toString().toDouble())
        }

    }
}
//points!!.toDouble() - binding.usdInput.text.toString().toDouble()