package no.kristiania.coinhub.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.entities.Transaction

class BuyCurrencyViewModel : ViewModel() {

    private lateinit var transactionDao : TransactionDAO

    fun init (context : Context) {
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
    }

    fun addTransaction(symbol : String, volume :Double, type :String, rate : Double, cost : Double){
        viewModelScope.launch {
            transactionDao.insert(Transaction(volume = volume, type = type, symbol = symbol, rate = rate))
            updateUserPoints(cost)
        }
    }

    private fun updateUserPoints(cost: Double){
        viewModelScope.launch {
            transactionDao.update(Transaction(type = "installationReward", volume = cost, rate = 0.toDouble(), symbol = "USD"))
            Log.d("update", "updated!! $cost")
        }
    }

    fun getEverything(){
        viewModelScope.launch {
            var data = withContext(Dispatchers.IO){
                transactionDao.getTransactions()
            }
            Log.d("data", data.toString())
        }
    }
}