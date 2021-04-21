package no.kristiania.coinhub.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.kristiania.coinhub.models.CurrencyStats
import no.kristiania.coinhub.repos.CurrencyRepo

class MainViewModel : ViewModel() {

    val liveStats = MutableLiveData<List<CurrencyStats>> (ArrayList<CurrencyStats>())
    private var repo = CurrencyRepo()

    fun refresh() {
        viewModelScope.launch {

            var result = withContext(Dispatchers.IO){
                repo.getCurrencySummary()
            }
            liveStats.value = result
        }
    }
}