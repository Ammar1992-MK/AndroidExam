package no.kristiania.coinhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import no.kristiania.coinhub.databinding.PortfolioLayoutBinding

class PortfolioActivity : AppCompatActivity() {

    private lateinit var binding : PortfolioLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PortfolioLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val points = intent.getSerializableExtra("POINTS")

        binding.currencyValue.text = points.toString()
    }
}