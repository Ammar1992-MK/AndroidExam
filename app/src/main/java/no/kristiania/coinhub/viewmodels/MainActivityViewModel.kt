package no.kristiania.coinhub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.db.TransactionHistoryDAO
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.entities.TransactionHistory

class MainActivityViewModel : ViewModel() {

    private lateinit var transactionDao: TransactionDAO
    private lateinit var transactionHistoryDao: TransactionHistoryDAO
    fun init (context : Context){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
        transactionHistoryDao = DataBase.getDatabase(context).getTransactionHistoryDAO()

    }

    fun insertInstallationReward(){
        viewModelScope.launch {
            transactionDao.insert(Transaction(type = "installationReward", volume = 10000.0,rate = 0.0, symbol = "USD"))
        }
    }

    fun saveInstallationRewardInHistory(){

        viewModelScope.launch {
            transactionHistoryDao.insert(TransactionHistory(type = "installationReward",volume = 10000.0,symbol = "USD",rate = 0.0))
        }
    }
}