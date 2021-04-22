package no.kristiania.coinhub

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import no.kristiania.coinhub.adapters.CurrencyListAdapter
import no.kristiania.coinhub.databinding.CurrencyListFragmentBinding
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.viewmodels.MainViewModel

class CurrencyListFragment : Fragment(R.layout.currency_list_fragment) {


    private lateinit var binding: CurrencyListFragmentBinding
    private lateinit var listAdapter :CurrencyListAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel = MainViewModel()

    companion object{

        fun newInstance() = CurrencyListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = CurrencyListFragmentBinding.bind(view)


        sharedPreferences = requireActivity().getSharedPreferences("no.kristiania.coinhub", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("FIRST_RUN", true).apply()
        var firstRun = sharedPreferences.getBoolean("FIRST_RUN", true)

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
        viewModel.init(requireContext(),firstRun)

        //get USD points
        viewModel.Points.observe(viewLifecycleOwner){

            binding.currencyValue.text = it.toString()

            Log.d("points", it.toString())
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
        sharedPreferences.edit().putBoolean("FIRST_RUN", false).apply()
    }

}
