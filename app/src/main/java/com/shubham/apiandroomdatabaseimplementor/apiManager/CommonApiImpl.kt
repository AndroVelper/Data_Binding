package com.shubham.apiandroomdatabaseimplementor.apiManager

import com.shubham.apiandroomdatabaseimplementor.utils.ErrorClass
import com.shubham.apiandroomdatabaseimplementor.utils.Status

data class Resource<out T>(val status: Status, val data: T?, val error: ErrorClass?) {
    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.Success, data, null)
        }

        fun <T> error(error: ErrorClass): Resource<T> {
            return Resource(Status.Error, null, error)
        }

        fun <T> databaseError(error: ErrorClass): Resource<T> {
            return Resource(Status.DbError, null, error)
        }



        fun <T> loading(): Resource<T> {
            return Resource(Status.IsLoading, null, null)
        }
    }
}