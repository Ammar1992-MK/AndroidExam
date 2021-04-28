package no.kristiania.coinhub.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import no.kristiania.coinhub.db.DataBase
import no.kristiania.coinhub.db.TransactionDAO

class PortfolioViewModel : ViewModel() {

    private lateinit var transactionDao: TransactionDAO

    fun init(context: Context){
        transactionDao = DataBase.getDatabase(context).getTransactionDAO()
    }
}