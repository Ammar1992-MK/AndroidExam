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


class PortfolioListViewModel : ViewModel() {

    private lateinit var transactionDAO: TransactionDAO

    private val _transactionListLiveData: MutableLiveData<List<Transaction>> = MutableLiveData()
    val transactionListLiveData: LiveData<List<Transaction>> = _transactionListLiveData

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
}