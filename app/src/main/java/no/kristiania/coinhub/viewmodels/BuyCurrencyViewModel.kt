package no.kristiania.coinhub.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO
import no.kristiania.coinhub.db.TransactionHistoryDAO
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.entities.TransactionHistory
import java.util.function.DoubleToLongFunction

class BuyCurrencyViewModel : ViewModel() {

    private lateinit var transactionDao : TransactionDAO
    private lateinit var transactionHistoryDAO: TransactionHistoryDAO

    private val _points = MutableLiveData<Double>()
    val points : LiveData<Double> get() = _points

    private val _currencyVolume = MutableLiveData<Double>()
    val currencyVolume : LiveData<Double> get() = _currencyVolume


    fun init (context : Context) {
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
        transactionHistoryDAO = DataBase.getDatabase(context).getTransactionHistoryDAO()
    }

    fun addTransaction(symbol : String, volume :Double, type :String, rate : Double){
        viewModelScope.launch {
            transactionDao.insert(Transaction(volume = volume, type = type, symbol = symbol, rate = rate))
        }
    }

    fun saveTransactionHistory(currencyOutput : Double, symbol : String, USDInput : Double){
       viewModelScope.launch {
           transactionHistoryDAO.insert(TransactionHistory(volume = currencyOutput, type = "Bought", symbol = symbol, rate = USDInput))
       }
    }

    fun getReward (){
        viewModelScope.launch {
            var userPoints = withContext(Dispatchers.IO){
                transactionDao.getReward("installationReward")
            }

            _points.value = userPoints
        }
    }

    fun updateCurrency(cost: Double, symbol : String){
        viewModelScope.launch {
            transactionDao.updateCurrency(cost, symbol)
            Log.d("update", "updated!! $cost")
        }
    }
    fun getCurrencyVolume(symbol : String){
        viewModelScope.launch {
            _currencyVolume.value =  transactionDao.getCurrency(symbol)
        }
    }

}