package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import no.kristiania.coinhub.adapters.PortfolioAdapter
import no.kristiania.coinhub.databinding.PortfolioLayoutBinding
import no.kristiania.coinhub.viewmodels.PortfolioListViewModel

class PortfolioListFragment : Fragment(R.layout.portfolio_layout) {

    private lateinit var binding : PortfolioLayoutBinding
    private val adapter = PortfolioAdapter()
    private val viewModel : PortfolioListViewModel by viewModels()

    companion object{

        fun newInstance() = PortfolioListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PortfolioLayoutBinding.bind(view)

        var points = arguments?.getString("points")
        var recentRate = arguments?.getDouble("recentRate")

        binding.transactions.setOnClickListener {

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment,TransactionHistoryListFragment.newInstance())
                .addToBackStack("transactions")
                .commit()
        }

        //init view model
        viewModel.init(requireContext())

        viewModel.transactionListLiveData.observe(viewLifecycleOwner){
            adapter.setPortfolioList(it)
        }




        //configure list
        configureList()
    }

    private fun configureList(){
        binding.portfolioRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.portfolioRecyclerView.adapter = adapter
    }

}