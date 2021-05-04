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
import no.kristiania.coinhub.db.TransactionHistoryDAO

class SellCurrencyViewModel : ViewModel() {

    private val _currencyVolume = MutableLiveData<Double>()
    val currencyVolume : LiveData<Double> get() = _currencyVolume

    private val _USDpoints = MutableLiveData<Double>()
    val USDpoints : LiveData<Double> get() = _USDpoints
    private lateinit var transactionDao : TransactionDAO
    private lateinit var transactionHistoryDAO: TransactionHistoryDAO


    fun init (context : Context) {
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
        getUSDVolume()
    }

    fun getCurrencyVolume( symbol : String){
        viewModelScope.launch {
            val volume = withContext(Dispatchers.IO){
                transactionDao.getCurrency(symbol)
            }
            _currencyVolume.value = volume
        }
    }

    fun updateCurrency(cost: Double, symbol : String){
        viewModelScope.launch {
            transactionDao.updateCurrency(cost, symbol)

        }
    }

    private fun getUSDVolume(){
        viewModelScope.launch {
            val volume = withContext(Dispatchers.IO){
                transactionDao.getCurrency("USD")
            }
            _USDpoints.value = volume
        }
    }
}