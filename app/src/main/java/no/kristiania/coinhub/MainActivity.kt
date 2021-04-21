package no.kristiania.coinhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
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

        listAdapter = CurrencyListAdapter(ArrayList<CurrencyStats>()){ stats ->
            var name = stats.Name
            var symbol = stats.Symbol
            var priceUsd = stats.PriceUsd

             Intent(this, CryptoCurrencyActivity::class.java).also {
                 it.putExtra("NAME", name)
                 it.putExtra("SYMBOL", symbol)
                 it.putExtra("PRICEUSD", priceUsd)
                 startActivity(it)
                 finish()
             }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = listAdapter


        binding.points.setOnClickListener {
            val pointsValue = binding.currencyValue.text.toString()
            Intent(this, PortfolioActivity::class.java).also {
                it.putExtra("POINTS", pointsValue)
                startActivity(it)
                finish()
            }
        }

        viewModel.liveStats.observe(this, { list->
            listAdapter.update(list)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}


