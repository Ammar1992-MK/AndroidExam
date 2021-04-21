package no.kristiania.coinhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import no.kristiania.coinhub.adapters.CurrencyListAdapter
import no.kristiania.coinhub.databinding.ActivityMainBinding
import no.kristiania.coinhub.datasources.DummySource
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var listAdapter :CurrencyListAdapter
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = CurrencyListAdapter(ArrayList<CurrencyStats>())

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = listAdapter

        viewModel.liveStats.observe(this, { list->
            listAdapter.update(list)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}