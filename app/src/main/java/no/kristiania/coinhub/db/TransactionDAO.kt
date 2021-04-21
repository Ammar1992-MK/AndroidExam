package no.kristiania.coinhub.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import no.kristiania.coinhub.entities.Transaction

@Dao
interface TransactionDAO {

    @Insert
    suspend fun insert(transaction : Transaction)


    @Query("select * from transaction_table order by id")
    suspend fun getTransactions() : List<Transaction>


}