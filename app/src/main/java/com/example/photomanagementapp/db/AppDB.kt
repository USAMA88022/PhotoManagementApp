package com.example.photomanagementapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photomanagementapp.DataModel.Bookmark

@Database(entities = [Bookmark::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

   abstract fun imageDao() : ImagesDao

companion object {

    private var instance: AppDB?=null
    fun getDatabase(context: Context): AppDB{
        if(instance==null){
            synchronized(this){
                instance= Room.databaseBuilder(
                    context,
                    AppDB::class.java,
                    "BookMark_ DB"
                )
                    .build()
            }
        }
            return instance!!
    }

}
}