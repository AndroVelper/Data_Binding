package com.shubham.apiandroomdatabaseimplementor.roomDatabase

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse


@Database(entities = [ApiResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): IDaoImplementor
}

object DatabaseManager {


    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database"
            ).build()
            INSTANCE = instance
            instance
        }


    }


}