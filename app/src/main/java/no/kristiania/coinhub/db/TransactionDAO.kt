package no.kristiania.coinhub.db

import androidx.room.*
import kotlinx.coroutines.selects.select

import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.entities.TransactionHistory

@Dao
interface TransactionDAO {

    //transaction_table

    @Insert
    suspend fun insert(transaction : Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Query("select volume from transaction_table where type =:type")
    suspend fun getReward(type : String) : Double

    @Query("select volume from transaction_table where symbol =:symbol")
    suspend fun getCurrency(symbol : String) : Double

    @Query("update transaction_table set volume =:newVolume where symbol=:symbol ")
    suspend fun updateCurrency(newVolume : Double, symbol: String)

    @Query("select * from transaction_table order by id")
    suspend fun getTransactions() : List<Transaction>

    //transaction_history_table

    @Insert
    suspend fun save(transactionHistory: TransactionHistory)

    @Query("select * from transaction_history_table order by id")
    suspend fun getHistory() : List<TransactionHistory>
}