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
import no.kristiania.coinhub.db.TransactionHistoryDAO
import no.kristiania.coinhub.entities.TransactionHistory

class TransactionHistoryListViewModel : ViewModel() {

    private lateinit var transactionHistoryDAO: TransactionHistoryDAO

    private val _transactionHistoryListLiveData: MutableLiveData<List<TransactionHistory>> = MutableLiveData()
    val transactionHistoryListLiveData: LiveData<List<TransactionHistory>> = _transactionHistoryListLiveData

    fun init(context: Context) {
        transactionHistoryDAO = DataBase.getDatabase(context).getTransactionHistoryDAO()
        getTransactions()
    }

    private fun getTransactions(){
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO){
                transactionHistoryDAO.getHistory()
            }
            _transactionHistoryListLiveData.value = list
        }
    }
}