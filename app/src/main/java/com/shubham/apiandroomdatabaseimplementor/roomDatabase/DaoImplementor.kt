package com.shubham.apiandroomdatabaseimplementor.roomDatabase

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse

@Dao
interface IDaoImplementor {


    @Upsert
    suspend fun upsertIntoDatabase(list: ArrayList<ApiResponse>):List<Long>

    @Query("SELECT COUNT(*) FROM local_table")
    suspend fun getCount(): Int

    @Query("DELETE FROM local_table")
    suspend fun deleteFromDatabase() : Int

    @Query("SELECT * FROM local_table LIMIT :limit OFFSET :offset")
    suspend fun getData(limit: Int = 20, offset: Int): List<ApiResponse>

}