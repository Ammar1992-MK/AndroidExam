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

class CryptoCurrencyViewModel : ViewModel() {

    private lateinit var transactionDao : TransactionDAO

    private val _currencyVolumeLiveData = MutableLiveData<Double>()
    val currencyVolumeLiveData : LiveData<Double> get() = _currencyVolumeLiveData

    private val _buyEnabled = MutableLiveData<Boolean>()
    val buyEnabled : LiveData<Boolean> get() = _buyEnabled

    fun init (context : Context, symbol: String){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()

        viewModelScope.launch {
            _currencyVolumeLiveData.value = transactionDao.getCurrency(symbol)
        }

        viewModelScope.launch {
            var points = withContext(Dispatchers.IO){

                transactionDao.getCurrency("USD")
            }
            _buyEnabled.value = points >= 1
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

