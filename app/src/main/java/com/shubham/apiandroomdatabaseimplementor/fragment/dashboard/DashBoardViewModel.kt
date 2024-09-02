package com.shubham.apiandroomdatabaseimplementor.fragment.dashboard

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shubham.apiandroomdatabaseimplementor.apiManager.Resource
import com.shubham.apiandroomdatabaseimplementor.apiManager.RetroFitInstance
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse
import com.shubham.apiandroomdatabaseimplementor.repository.Repository
import com.shubham.apiandroomdatabaseimplementor.roomDatabase.DatabaseManager
import com.shubham.apiandroomdatabaseimplementor.utils.ErrorClass
import com.shubham.apiandroomdatabaseimplementor.utils.showToast
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DashBoardViewModel(application: Application) : AndroidViewModel(application) {

    private var _apiResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val apiResponse get() = _apiResponse

    var isRefreshing: MutableLiveData<Boolean> = MutableLiveData(true)
    var totalItemCount: MutableLiveData<String?> = MutableLiveData()
    var visibleItemCount: MutableLiveData<String> = MutableLiveData("0")

    private var repository: Repository? = null


    init {
        val roomDatabase = DatabaseManager.getDatabase(application.applicationContext).roomDao()
        val apiInstance = RetroFitInstance.apiService
        repository = Repository.getInstance(roomDatabase, apiInstance)

    }

    fun getAllData(view: View? = null) {
        isRefreshing.postValue(true)
        _apiResponse.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository?.getDataFromApi()
                if (response?.isSuccessful == true) {
                    insertIntoDatabase(response.body())
                } else {
                    _apiResponse.postValue(Resource.error(ErrorClass(response?.message() ?: "")))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isRefreshing.postValue(false)
            }


        }
    }

    fun deleteFromDatabase(view: View?): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                if (view != null) {
                    totalItemCount.postValue("0")
                    visibleItemCount.postValue("0")
                    _apiResponse.postValue(Resource.databaseError(ErrorClass("empty")))
                }

                val response = repository?.deleteFromDatabase()
                if (response != null && response <= 0) {
                    "Database is Empty Successfully".showToast(getApplication<Application>().applicationContext)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun isDatabaseEmpty(): Int? {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository?.isDatabaseEmpty()
                if (response != null && response <= 0) {
                    getAllData()
                } else {
                    getDataFromDatabase()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    private suspend fun isDatabase() {
        var job: Deferred<Unit>? = null
        val response = repository?.isDatabaseEmpty() ?: return
        if (response > 0) {
            job = viewModelScope.async { deleteFromDatabase(null) }
        }
        job?.await()
    }


    private fun insertIntoDatabase(body: ArrayList<ApiResponse>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                body ?: return@launch
                isDatabase()
                val response = repository?.insertIntoDatabase(body)
                if (response?.isNotEmpty() == true) {
                    getDataFromDatabase()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDataFromDatabase(offset: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                totalItemCount.postValue(repository?.isDatabaseEmpty().toString())
                val response = repository?.getDataFromRoom(offset)
                if (!response.isNullOrEmpty()) {
                    val totalItems = offset + response.size
                    visibleItemCount.postValue(totalItems.toString())
                    _apiResponse.postValue(Resource.success(response))
                }
            } catch (e: Exception) {
                _apiResponse.postValue(Resource.error(ErrorClass("Failed to read data from database ")))
                e.printStackTrace()
            }

        }
    }


}