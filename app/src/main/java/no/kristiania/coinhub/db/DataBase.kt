package no.kristiania.coinhub.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import no.kristiania.coinhub.entities.Transaction
import no.kristiania.coinhub.entities.TransactionHistory

const val DATABASE_NAME : String = "transactions_database"

@Database(entities = [Transaction::class , TransactionHistory::class], version = 2)
abstract class DataBase : RoomDatabase(){

    abstract fun getTransactionDAO() : TransactionDAO

    abstract fun getTransactionHistoryDAO() : TransactionHistoryDAO


    companion object{

        var db : DataBase? = null

        fun getDatabase(context : Context) : DataBase{

            val newDB = db?: Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME).addMigrations(
                MIGRATION_1_2).build()

            return newDB.also {
                db = it
            }
        }

        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("CREATE TABLE `TransactionHistory` (`id` INTEGER, `type` TEXT,`volume` INTEGER, `rate` INTEGER, `symbol` TEXT " +
                        "PRIMARY KEY(`id`))")
            }

        }
    }

}