package com.example.photomanagementapp.DataModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var id: Int?,
val title: String, val uri: String
)
