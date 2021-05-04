package no.kristiania.coinhub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import no.kristiania.coinhub.adapters.TransactionHistoryAdapter
import no.kristiania.coinhub.databinding.TransactionHistoryLayoutBinding
import no.kristiania.coinhub.viewmodels.TransactionHistoryListViewModel

class TransactionHistoryListFragment : Fragment(R.layout.transaction_history_layout) {

    private lateinit var binding : TransactionHistoryLayoutBinding
    private val adapter = TransactionHistoryAdapter()
    private val viewModel : TransactionHistoryListViewModel by viewModels()

    companion object{

        fun newInstance() = TransactionHistoryListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TransactionHistoryLayoutBinding.bind(view)

        //init view model
        viewModel.init(requireContext())

        viewModel.transactionHistoryListLiveData.observe(viewLifecycleOwner){
            adapter.setTransactionHistoryList(it)
        }

        //configure list
        configureList()
    }

    private fun configureList(){
        binding.recyclerViewTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTransactions.adapter = adapter
    }

}