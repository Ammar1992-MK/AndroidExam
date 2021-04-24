package no.kristiania.coinhub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.entities.Transaction

class MainActivityViewModel : ViewModel() {

    private lateinit var transactionDao: TransactionDAO
    fun init (context : Context){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
    }

    fun insertInstallationReward(){
        viewModelScope.launch {
            transactionDao.insert(Transaction(type = "installationReward", volume = 10000.toDouble(),rate = 0.toDouble(), symbol = "USD"))
        }
    }
}