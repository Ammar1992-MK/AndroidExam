package no.kristiania.coinhub.viewmodels
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.kristiania.coinhub.db.DataBase.Companion.getDatabase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.models.RateStats
import no.kristiania.coinhub.repos.CurrencyRepo


class PortfolioListViewModel : ViewModel() {

    private lateinit var transactionDAO: TransactionDAO

    private val _transactionListLiveData: MutableLiveData<List<Transaction>> = MutableLiveData()
    val transactionListLiveData: LiveData<List<Transaction>> = _transactionListLiveData

    private val _recentRate: MutableLiveData<List<RateStats>> = MutableLiveData()
    val recentRate: LiveData<List<RateStats>> = _recentRate
    private var repo = CurrencyRepo()

    fun init(context: Context) {
        transactionDAO = getDatabase(context).getTransactionDAO()
        getTransactions()
    }

    private fun getTransactions(){
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO){
                transactionDAO.getTransactions()
            }
            _transactionListLiveData.value = list
        }
    }

    fun getRecentRate(currencyName : String){

        viewModelScope.launch {
            var list = withContext(Dispatchers.IO){
                repo.getRecentRate(currencyName)
            }
            _recentRate.value = list
        }
    }
}