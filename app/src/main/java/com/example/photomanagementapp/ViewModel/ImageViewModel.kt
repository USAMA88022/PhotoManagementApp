package com.example.photomanagementapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photomanagementapp.DataModel.imagesModel
import com.example.photomanagementapp.Repository.ImageRepository
import com.example.photomanagementapp.DataModel.Bookmark
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageViewModel(val repository: ImageRepository) : ViewModel() {

val imagesData :MutableLiveData<List<imagesModel>> = repository.imagesLiveData

    fun getImages(){
        repository.getImages()
    }

    fun getImagesLiveData():LiveData<List<imagesModel>>{
        repository.fetchImagesLiveData()
        return imagesData
    }


    // Bookmark db //

    val addimages: LiveData<List<Bookmark>> = repository.getAllData()

    fun insertData(title: String, url: String){
        GlobalScope.launch {
            val data = Bookmark(null,title,url)
            repository.insertData(data)
        }
    }
    fun deleteData(bookMark: Bookmark){
        GlobalScope.launch {
            repository.deleteData(bookMark)
        }
    }

}