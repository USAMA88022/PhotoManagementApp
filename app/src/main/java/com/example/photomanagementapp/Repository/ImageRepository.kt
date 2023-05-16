package com.example.photomanagementapp.Repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photomanagementapp.DataModel.imagesModel
import com.example.photomanagementapp.db.AppDB
import com.example.photomanagementapp.DataModel.Bookmark

class ImageRepository(val context: Context, private val appDB: AppDB) {

    val imagesLiveData =MutableLiveData<List<imagesModel>>()

    // Fetch all Images from devices DCIM Folder


    fun getImages(){
        val images = mutableListOf<imagesModel>()

        val resolver = context.contentResolver
        val cursor =resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )
        if(cursor!=null && cursor.moveToFirst()){

            do{
                val uri = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)))
                val name =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(imagesModel(name, uri.toString()))
            }
                while(cursor.moveToNext())
                cursor.close()
        }
        imagesLiveData.postValue(images)
    }

    fun fetchImagesLiveData(): LiveData<List<imagesModel>> {
        return imagesLiveData

    }

    // for bookMark db //
    fun getAllData():LiveData<List<Bookmark>>{
        return appDB.imageDao().getAllData()
    }

    suspend fun insertData(bookmark: Bookmark){
        appDB.imageDao().dataInsert(bookmark)
    }
     suspend fun deleteData(bookmark: Bookmark){
        appDB.imageDao().deleteData(bookmark)
}



}