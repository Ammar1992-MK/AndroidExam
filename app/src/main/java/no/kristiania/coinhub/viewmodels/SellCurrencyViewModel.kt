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
import no.kristiania.coinhub.db.TransactionDAO

class SellCurrencyViewModel : ViewModel() {

    private val _currencyVolume = MutableLiveData<Double>()
    val currencyVolume : LiveData<Double> get() = _currencyVolume
    private lateinit var transactionDao : TransactionDAO


    fun init (context : Context) {
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
    }

    fun getCurrencyVolume( symbol : String){
        viewModelScope.launch {
            val volume = withContext(Dispatchers.IO){
                transactionDao.getCurrency(symbol)
            }
            _currencyVolume.value = volume
        }
    }
}