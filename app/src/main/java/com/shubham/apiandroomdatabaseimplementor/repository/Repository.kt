package com.shubham.apiandroomdatabaseimplementor.repository

import com.shubham.apiandroomdatabaseimplementor.apiManager.IApiCalls
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse
import com.shubham.apiandroomdatabaseimplementor.roomDatabase.IDaoImplementor

class Repository private constructor() {


    companion object {
        private var instance: Repository? = null
        private var databaseManager: IDaoImplementor? = null
        private var apiCaller: IApiCalls? = null

        fun getInstance(databaseManager: IDaoImplementor, retrofitInstance: IApiCalls): Repository {

            if (this.databaseManager == null) {
                this.databaseManager = databaseManager
            }

            if (this.apiCaller == null) {
                this.apiCaller = retrofitInstance
            }

            return instance ?: synchronized(this) {
                Repository().also {
                    instance = it
                }
            }
        }
    }


    // api call
    suspend fun getDataFromApi() = apiCaller?.getAllPhotos()

    // check database is empty
    suspend fun isDatabaseEmpty()  = databaseManager?.getCount()

    // insert into database
    suspend fun insertIntoDatabase(data: ArrayList<ApiResponse>) =
        databaseManager?.upsertIntoDatabase(data)

    // delete query
    suspend fun deleteFromDatabase() = databaseManager?.deleteFromDatabase()

    // get the required data
    suspend fun getDataFromRoom(offset:Int) = databaseManager?.getData(offset = offset)

}