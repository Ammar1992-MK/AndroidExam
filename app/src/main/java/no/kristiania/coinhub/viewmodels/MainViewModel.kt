package no.kristiania.coinhub.viewmodels

import android.content.Context
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
    private val _Points = MutableLiveData<Float>()
    val Points : LiveData<Float> get() = _Points
    private var repo = CurrencyRepo()
    private lateinit var transactionDao: TransactionDAO

    fun init (context : Context){

        transactionDao = DataBase.getDatabase(context).getTransactionDAO()

        viewModelScope.launch {

            _Points.value =   transactionDao.getCurrency("USD")
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

    fun saveInstallationReward (symbol : String, volume : Float){

        viewModelScope.launch {

            if(symbol.isNullOrEmpty() || volume.isNaN()){
                return@launch
            }

            transactionDao.insert(Transaction(symbol = symbol, volume = volume, rate = 0, type = "reward"))
        }
    }

}