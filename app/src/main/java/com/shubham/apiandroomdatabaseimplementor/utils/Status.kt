package com.shubham.apiandroomdatabaseimplementor.utils

enum class Status {
    Success,
    Error,
    DbError,
    IsLoading
}

data class ErrorClass(
    val message : String
)