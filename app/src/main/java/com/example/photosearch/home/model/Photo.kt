package com.example.photosearch.home.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photosearch.core.Constants

@Entity(tableName = Constants.PHOTO_ENTITY)
open class Photo(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    @Embedded val url: PhotoUrl,
    val authorName: String,
    var isFavourite: Boolean,
    val altDescription: String
) {
    constructor(detail: Photo) : this(
        detail.id,
        detail.title,
        detail.description,
        detail.url,
        detail.authorName,
        detail.isFavourite,
        detail.altDescription
    )
}