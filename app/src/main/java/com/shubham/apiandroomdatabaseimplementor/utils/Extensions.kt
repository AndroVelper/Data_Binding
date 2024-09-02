package com.shubham.apiandroomdatabaseimplementor.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun Fragment.suspendOnMainThread(task: () -> Unit) {
    withContext(Dispatchers.Main) {
        task()
    }
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun String.showToast(context : Context){
    Toast.makeText(context, this , Toast.LENGTH_SHORT).show()
}