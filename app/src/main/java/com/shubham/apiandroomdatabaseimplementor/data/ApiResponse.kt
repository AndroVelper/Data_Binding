package com.shubham.apiandroomdatabaseimplementor.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("local_table")
data class ApiResponse(
    @PrimaryKey(autoGenerate = true)
    val refId : Int = 0,
    val albumId: Int?,
    val id: String?,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)