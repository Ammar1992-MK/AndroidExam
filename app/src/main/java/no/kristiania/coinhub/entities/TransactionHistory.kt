package no.kristiania.coinhub.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transaction_history_table")
data class TransactionHistory (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long = 0,

    @ColumnInfo(name = "type")
    val type : String,

    @ColumnInfo(name = "volume")
    val volume : Double,

    @ColumnInfo(name = "rate")
    val rate : Double,

    @ColumnInfo(name = "symbol")
    val symbol : String,

    )