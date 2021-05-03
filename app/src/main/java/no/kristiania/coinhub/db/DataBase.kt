package no.kristiania.coinhub.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.entities.TransactionHistory

const val DATABASE_NAME : String = "transactions_database"

@Database(entities = [Transaction::class , TransactionHistory::class], version = 1)
abstract class DataBase : RoomDatabase(){

    abstract fun getTransactionDAO() : TransactionDAO

    companion object{

        var db : DataBase? = null

        fun getDatabase(context : Context) : DataBase{

            val newDB = db?: Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME).build()

            return newDB.also {
                db = it
            }
        }
    }
}