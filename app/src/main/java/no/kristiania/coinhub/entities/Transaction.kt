package no.kristiania.coinhub.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "transaction_table")
 data class Transaction (

    @ColumnInfo(name = "id")
    val id:Long = 0,

    @ColumnInfo(name = "type")
    val type : String,

    @ColumnInfo(name = "volume")
    val volume : Float,

    @ColumnInfo(name = "rate")
    val rate : Int,

    @ColumnInfo(name = "symbol")
    val symbol : String,


         )