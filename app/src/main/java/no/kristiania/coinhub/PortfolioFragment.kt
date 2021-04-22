package no.kristiania.coinhub

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import no.kristiania.coinhub.databinding.PortfolioLayoutBinding

class PortfolioFragment : Fragment(R.layout.portfolio_layout) {

    private lateinit var binding : PortfolioLayoutBinding

    companion object{
        fun newInstance() = PortfolioFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PortfolioLayoutBinding.bind(view)

        Log.d("start", "Called")


        var points = arguments?.getString("points")

        binding.currencyValue.text = points
    }
}