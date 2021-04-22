package no.kristiania.coinhub.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO

class CryptoCurrencyViewModel : ViewModel() {

    private lateinit var transactionDao : TransactionDAO

    private val _currencyVolumeLiveData = MutableLiveData<Float>()
    val currencyVolumeLiveData : LiveData<Float> get() = _currencyVolumeLiveData

    private val _buyEnabled = MutableLiveData<Boolean>()
    val buyEnabled : LiveData<Boolean> get() = _buyEnabled

    fun init (context : Context, symbol: String){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()

        viewModelScope.launch {
            _currencyVolumeLiveData.value = transactionDao.getCurrency(symbol)
        }

        viewModelScope.launch {
            val points = transactionDao.getCurrency("USD")
            _buyEnabled.value = points >= 1
        }
    }

}

