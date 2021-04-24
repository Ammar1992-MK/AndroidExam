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
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.repos.CurrencyRepo

class CurrencyListViewModel : ViewModel() {

    val liveStats = MutableLiveData<List<CurrencyStats>> (ArrayList<CurrencyStats>())
    private val _points = MutableLiveData<Double>()
    val points : LiveData<Double> get() = _points
    private var repo = CurrencyRepo()
    private lateinit var transactionDao: TransactionDAO

    fun init (context : Context){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
        viewModelScope.launch {
            _points.value =   transactionDao.getReward("installationReward")
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