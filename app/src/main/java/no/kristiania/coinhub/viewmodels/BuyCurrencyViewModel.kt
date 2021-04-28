package no.kristiania.coinhub.viewmodels

import android.content.Context
import android.util.Log
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
import kotlin.math.cos

class BuyCurrencyViewModel : ViewModel() {

    private lateinit var transactionDao : TransactionDAO

    private val _points = MutableLiveData<Double>()
    val points : LiveData<Double> get() = _points

    private val _currencyVolume = MutableLiveData<Double>()
    val currencyVolume : LiveData<Double> get() = _currencyVolume


    fun init (context : Context) {
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
    }

    fun addTransaction(symbol : String, volume :Double, type :String, rate : Double){
        viewModelScope.launch {
            transactionDao.insert(Transaction(volume = volume, type = type, symbol = symbol, rate = rate))

        }
    }

    fun getReward (){
        viewModelScope.launch {
            var userPoints = withContext(Dispatchers.IO){
                transactionDao.getReward("installationReward")
            }

            _points.value = userPoints;
        }
    }

    fun updateUserPoints(cost: Double){
        viewModelScope.launch {
            transactionDao.updateUserPoints(cost, "USD")
            Log.d("update", "updated!! $cost")
        }
    }
    fun getCurrencyVolume(symbol : String){
        viewModelScope.launch {
            var volume = withContext(Dispatchers.IO){
                transactionDao.getCurrency(symbol)
            }

            _currencyVolume.value = volume
        }
    }

}