package com.shubham.apiandroomdatabaseimplementor.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse

class DiffUtilsManager(
    private val oldList: ArrayList<ApiResponse>,
    private val newList: ArrayList<ApiResponse>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    // verify the id's
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    // verify the content
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}