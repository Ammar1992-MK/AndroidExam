package no.kristiania.coinhub.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transaction_table")
data class Transaction (

   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   val id:Long = 0,

   @ColumnInfo(name = "type")
   val type : String,

   @ColumnInfo(name = "volume")
   val volume : Double,

   @ColumnInfo(name = "rate")
   val rate : Int,

   @ColumnInfo(name = "symbol")
   val symbol : String,


   )