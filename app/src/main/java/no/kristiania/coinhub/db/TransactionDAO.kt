package no.kristiania.coinhub.db

import androidx.room.*
import kotlinx.coroutines.selects.select

import no.kristiania.coinhub.entities.Transaction

@Dao
interface TransactionDAO {

    @Insert
    suspend fun insert(transaction : Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Query("select volume from transaction_table where type =:type")
    suspend fun getReward(type : String) : Double

    @Query("select volume from transaction_table where symbol =:symbol")
    suspend fun getCurrency(symbol : String) : Double

    @Query("select * from transaction_table order by id")
    suspend fun getTransactions() : List<Transaction>


}