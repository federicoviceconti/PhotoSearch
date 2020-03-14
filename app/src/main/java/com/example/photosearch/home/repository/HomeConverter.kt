package com.example.photosearch.home.repository

import com.example.photosearch.home.model.Photo
import com.example.photosearch.home.model.PhotoUrl
import com.example.photosearch.services.networks.PhotoListResponse
import com.example.photosearch.services.networks.UrlResponse
import io.reactivex.Observable

fun Observable<PhotoListResponse>.convertPhotoList(): Observable<List<Photo>> =
    this.map {
        it.results?.map { photoItem ->
            Photo(
                title = "",
                altDescription = photoItem.altDescription.orEmpty(),
                description = photoItem.description.orEmpty(),
                url = photoItem.url.convertPhotoUrl(),
                isFavourite = false,
                authorName = "",
                id = photoItem.id.orEmpty()
            )
        }
    }

private fun UrlResponse?.convertPhotoUrl(): PhotoUrl = PhotoUrl(this?.thumb.orEmpty(), this?.small.orEmpty(), this?.regular.orEmpty())
