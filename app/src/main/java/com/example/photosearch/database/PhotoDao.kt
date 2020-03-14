package com.example.photosearch.database

import androidx.room.*
import com.example.photosearch.core.Constants
import com.example.photosearch.home.model.Photo

@Dao
interface PhotoDao {
    @Query("SELECT * from ${Constants.PHOTO_ENTITY}")
    fun getAllSavedPhotos() : List<Photo>

    @Query("SELECT * from ${Constants.PHOTO_ENTITY} where id = :id")
    fun getPhoto(id: String) : Photo?

    @Query("SELECT * from ${Constants.PHOTO_ENTITY} where isFavourite")
    fun getAllFavouritePhotos() : List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(photos: List<Photo>)

    @Query("DELETE FROM ${Constants.PHOTO_ENTITY}")
    suspend fun deleteAllPhotos()

    @Delete
    fun deletePhoto(photo: Photo)
}