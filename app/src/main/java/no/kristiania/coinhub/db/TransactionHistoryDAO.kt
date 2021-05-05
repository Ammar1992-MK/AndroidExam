package no.kristiania.coinhub.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.coinhub.entities.TransactionHistory

@Dao
interface TransactionHistoryDAO {

    @Insert
    suspend fun insert(transactionHistory: TransactionHistory)

    @Query("select * from transaction_history_table order by id desc")
    suspend fun getHistory() : List<TransactionHistory>
}