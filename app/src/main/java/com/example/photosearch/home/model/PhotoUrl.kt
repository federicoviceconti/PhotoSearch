package com.example.photosearch.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photosearch.core.Constants

@Entity(tableName = Constants.PHOTO_URL_ENTITY)
class PhotoUrl (
    val thumb: String,
    val small: String,
    val regular: String
)
