package com.example.photosearch.services

import com.example.photosearch.services.networks.PhotoListResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    companion object Constants {
        const val BASE_URL = "https://api.unsplash.com"
        const val ACCESS_KEY = "user-your-own-key"
        const val PER_PAGE = 10
    }

    @GET("/search/photos")
    fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = ACCESS_KEY
    ): Observable<PhotoListResponse>

    @GET("/photos")
    fun popularPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = ACCESS_KEY
    ): Observable<List<PhotoListResponse>>
}