package no.kristiania.coinhub.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.repos.CurrencyRepo

class MainViewModel : ViewModel() {

    val liveStats = MutableLiveData<List<CurrencyStats>> (ArrayList<CurrencyStats>())
    private val _Points = MutableLiveData<Double>()
    val Points : LiveData<Double> get() = _Points
    private var repo = CurrencyRepo()
    private lateinit var transactionDao: TransactionDAO

    fun init (context : Context, firstRun : Boolean){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()

        giveReward(firstRun)

        viewModelScope.launch {
            _Points.value =   transactionDao.getReward("reward")
        }

    }

    fun giveReward(firstRun: Boolean){
        viewModelScope.launch {
            if(firstRun){
                transactionDao.insert(Transaction(volume = 10000.toDouble(),type = "reward",rate = 0, symbol = "USD"))
            }
        }
    }



    fun refresh() {
        viewModelScope.launch {

            var result = withContext(Dispatchers.IO){
                repo.getCurrencySummary()
            }
            liveStats.value = result
        }
    }




}