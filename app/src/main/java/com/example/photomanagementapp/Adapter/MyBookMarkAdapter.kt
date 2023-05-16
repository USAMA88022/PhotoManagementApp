package com.example.photomanagementapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photomanagementapp.databinding.BookmarkLayoutBinding
import com.example.photomanagementapp.DataModel.Bookmark
import com.example.photomanagementapp.interfaces.onClickinterface

class MyBookMarkAdapter(var context: Context, var bookmarklist: List<Bookmark>, val onItemClick: onClickinterface) :  RecyclerView.Adapter<MyBookMarkAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: BookmarkLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =BookmarkLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return bookmarklist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = bookmarklist[position]
        holder.binding.imgTxt.text=data.title
        Glide.with(context).load(data.uri.toString()).thumbnail(0.1f).centerCrop().into(holder.binding.imageView)
        holder.binding.btndelete.setOnClickListener {
            onItemClick.onFavClick(data)
        }
    }
}