package com.shubham.apiandroomdatabaseimplementor.adapter

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.shubham.apiandroomdatabaseimplementor.R

@BindingAdapter("ivImage")
fun bindImage(view: ShapeableImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_new) // Optional placeholder image
        .error(R.drawable.ic_new) // Optional error image
        .into(view)
}