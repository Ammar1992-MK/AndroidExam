package no.kristiania.coinhub

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import no.kristiania.coinhub.databinding.PortfolioLayoutBinding
import no.kristiania.coinhub.viewmodels.PortfolioViewModel

class PortfolioFragment : Fragment(R.layout.portfolio_layout) {

    private lateinit var binding : PortfolioLayoutBinding
    private val viewModel: PortfolioViewModel by viewModels()

    companion object{
        fun newInstance() = PortfolioFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PortfolioLayoutBinding.bind(view)
        viewModel.init(requireContext())

        viewModel.transactionListLiveData.observe(viewLifecycleOwner){

        }

        Log.d("start", "Called")


        var points = arguments?.getString("points")

        binding.currencyValue.text = points
    }
}