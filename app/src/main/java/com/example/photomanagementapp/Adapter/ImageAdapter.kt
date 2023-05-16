package com.example.photomanagementapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photomanagementapp.DataModel.imagesModel
import com.example.photomanagementapp.databinding.ImageLayoutBinding
import com.example.photomanagementapp.interfaces.onClickinterface
import java.io.File

class ImageAdapter(var context:Context, var imageList: List<imagesModel>,var onItemClick: onClickinterface):RecyclerView.Adapter<ImageAdapter.MyViewHolder>(){
    class MyViewHolder(var binding: ImageLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ImageLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = imageList[position]
//        val file = File(data.uri.path.toString())
        holder.binding.imageViewname.text=data.name
        Glide.with(context).load(data.uri.toString()).thumbnail(0.1f).centerCrop().into(holder.binding.imageView)
//        Glide.with(context).load(file.absoluteFile).into(holder.binding.imageView)
        holder.binding.more.setOnClickListener { onItemClick.PopUpClick(data,holder.binding.more) }
        }

    }
