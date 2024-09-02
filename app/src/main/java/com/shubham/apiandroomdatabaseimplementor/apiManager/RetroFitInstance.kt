package com.shubham.apiandroomdatabaseimplementor.apiManager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// to work in the static manner we will implement this in the object
object RetroFitInstance {

    private val retrofitInstance = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService = retrofitInstance.create(IApiCalls::class.java)


}