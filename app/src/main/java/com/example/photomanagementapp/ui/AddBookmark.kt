package com.example.photomanagementapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photomanagementapp.Adapter.MyBookMarkAdapter
import com.example.photomanagementapp.DataModel.imagesModel
import com.example.photomanagementapp.Factory.MediaFactory
import com.example.photomanagementapp.Repository.ImageRepository
import com.example.photomanagementapp.ViewModel.ImageViewModel
import com.example.photomanagementapp.databinding.FragmentAddBookmarkBinding
import com.example.photomanagementapp.db.AppDB
import com.example.photomanagementapp.DataModel.Bookmark
import com.example.photomanagementapp.interfaces.onClickinterface


class AddBookmark : Fragment(), onClickinterface {
    private var  binding:FragmentAddBookmarkBinding?=null
    private var recyclerView: RecyclerView?=null
    private var imageViewModel: ImageViewModel?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddBookmarkBinding.inflate(LayoutInflater.from(context),container,false)

        recyclerView = binding?.recylerView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val db = AppDB.getDatabase(requireContext())
        val repository =ImageRepository(requireContext(),db)
        imageViewModel = ViewModelProvider(this,MediaFactory(repository)).get(ImageViewModel::class.java)
        imageViewModel?.addimages?.observe(viewLifecycleOwner, Observer {
            recyclerView?.adapter= MyBookMarkAdapter(requireContext(),it,this)
        })

        return binding?.root

    }


    override fun onImageClick(imageModel: imagesModel) {
        TODO("Not yet implemented")
    }

    override fun PopUpClick(imageModel: imagesModel, myView: View) {
        TODO("Not yet implemented")
    }

    override fun onFavClick(bookmark: Bookmark) {
        imageViewModel?.deleteData(bookmark)
    }

}