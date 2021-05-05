package no.kristiania.coinhub

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import no.kristiania.coinhub.databinding.ActivityMainBinding
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.viewmodels.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
   private val viewModel = MainActivityViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("no.kristiania.coinhub", MODE_PRIVATE)
        val firstRun = sharedPreferences.getBoolean("firstRun", true)

        //initViewModel
        viewModel.init(this)

        if(firstRun){
            viewModel.insertInstallationReward()
            viewModel.saveInstallationRewardInHistory()
            sharedPreferences.edit().putBoolean("firstRun", false).commit()
        }

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


