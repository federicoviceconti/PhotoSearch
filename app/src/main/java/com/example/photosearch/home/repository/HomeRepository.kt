package com.example.photosearch.home.repository

import com.example.photosearch.database.PhotoDao
import com.example.photosearch.database.TagDao
import com.example.photosearch.home.model.Photo
import com.example.photosearch.home.model.Tag
import com.example.photosearch.services.UnsplashService
import io.reactivex.Observable

class HomeRepository(val photoDao: PhotoDao, val tagDao: TagDao, private val service: UnsplashService) {
    fun getPhotosByTag(indexStart: Int, query: String): Observable<List<Photo>> {
        return service.searchPhotos(query, indexStart, UnsplashService.PER_PAGE).convertPhotoList()
    }

    fun getAllTags(): List<Tag> {
        return tagDao.getAllTags()
    }

    fun insertStandardTag() {
        val popularNewest = Tag("Popular", true)
        val photoNewest = Tag("Newest", false)

        tagDao.insertTags(mutableListOf(popularNewest, photoNewest))
    }
}
