package com.example.photosearch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.photosearch.home.model.Photo
import com.example.photosearch.home.model.Tag

@Database(entities = [Photo::class, Tag::class], version = 1, exportSchema = false)
abstract class PhotoSearchRoomDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun tagDao(): TagDao
}