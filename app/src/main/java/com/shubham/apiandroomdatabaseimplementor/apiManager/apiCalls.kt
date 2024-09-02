package com.shubham.apiandroomdatabaseimplementor.apiManager

import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface IApiCalls {


    @GET("/photos")
    suspend fun getAllPhotos(): Response<ArrayList<ApiResponse>>
}