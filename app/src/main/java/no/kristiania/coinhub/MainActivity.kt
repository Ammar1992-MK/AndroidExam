package no.kristiania.coinhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import no.kristiania.coinhub.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container_fragment,
                CurrencyListFragment.newInstance(),
                "CurrencyListFragments"
            )
            .commit()

    }


}


