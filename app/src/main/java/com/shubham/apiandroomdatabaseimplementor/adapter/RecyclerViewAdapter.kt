package com.shubham.apiandroomdatabaseimplementor.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse
import com.shubham.apiandroomdatabaseimplementor.databinding.ItemListBinding

class RecyclerViewAdapter(private var list : ArrayList<ApiResponse>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        return MyViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    // used diffutils to update the item that are required not all the items
    // Custom method to update the list
    fun updateList(updatedList : ArrayList<ApiResponse> ) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilsManager(list, updatedList))
        list = updatedList
        diffResult.dispatchUpdatesTo(this)


    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        val data = list[holder.adapterPosition]
        holder.bindWithBindings(data)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(private val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindWithBindings(item: ApiResponse) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}