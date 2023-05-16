package com.example.photomanagementapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.photomanagementapp.DataModel.Bookmark

@Dao
interface ImagesDao {

@Insert
suspend fun dataInsert(bookmark: Bookmark)

@Query
    ("SELECT * FROM my_bookmark")
    fun getAllData() :LiveData<List<Bookmark>>

    @Delete
    fun deleteData(bookMark: Bookmark)
}