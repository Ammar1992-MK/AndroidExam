package no.kristiania.coinhub

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import no.kristiania.coinhub.adapters.CurrencyListAdapter
import no.kristiania.coinhub.databinding.CurrencyListFragmentBinding
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.viewmodels.MainViewModel

class CurrencyListFragment : Fragment(R.layout.currency_list_fragment) {

    private lateinit var binding: CurrencyListFragmentBinding
    private lateinit var listAdapter :CurrencyListAdapter
    private var points = 1000
    private val viewModel = MainViewModel()

    companion object{

        fun newInstance() = CurrencyListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = CurrencyListFragmentBinding.bind(view)

        listAdapter = CurrencyListAdapter(ArrayList<CurrencyStats>()){ stats ->
          var fragment =  CryptoCurrencyFragments.newInstance()

            fragment.arguments = Bundle().apply {
                putString("name", stats.Name)
                putDouble("price", stats.PriceUsd)
                putString("symbol", stats.Symbol)
            }

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .addToBackStack("CurrencyList")
                .commit()

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = listAdapter

        //init view model
        viewModel.init(requireContext())
        //save reward
        viewModel.saveInstallationReward("USD", points.toFloat())

        //get USD points

        viewModel.Points.observe(viewLifecycleOwner){
            binding.currencyValue.text = it.toString()
        }
       binding.points.setOnClickListener {

           var fragment = PortfolioFragment.newInstance()

           fragment.arguments = Bundle().apply {

               putString("points", binding.currencyValue.text.toString())
           }
           parentFragmentManager
               .beginTransaction()
               .replace(
                   R.id.container_fragment,fragment)
               .addToBackStack("PortfolioFragment")
               .commit()
       }

        viewModel.liveStats.observe(viewLifecycleOwner){ list->
            listAdapter.update(list)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

}
