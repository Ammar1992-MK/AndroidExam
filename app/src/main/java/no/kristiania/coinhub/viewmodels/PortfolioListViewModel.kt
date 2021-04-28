package no.kristiania.coinhub.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import kotlinx.coroutines.launch
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.DataBase.Companion.getDatabase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.models.CurrencyStats

class PortfolioListViewModel : ViewModel() {

    private lateinit var transactionDAO: TransactionDAO

    private val _transactionListLiveData: MutableLiveData<List<CurrencyStats>> = MutableLiveData()
    val transactionListLiveData: LiveData<List<CurrencyStats>> = _transactionListLiveData

    fun init(context: Context) {
        transactionDAO = getDatabase(context).getTransactionDAO()
        getTransactions()
    }

    fun getTransactions(){
        viewModelScope.launch {
            val list = transactionDAO.getTransactions()
            _transactionListLiveData.value = list
        }
    }
}