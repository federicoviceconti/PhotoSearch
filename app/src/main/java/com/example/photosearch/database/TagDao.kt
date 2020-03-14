package com.example.photosearch.database

import androidx.room.*
import com.example.photosearch.core.Constants
import com.example.photosearch.home.model.Tag

@Dao
interface TagDao {
    @Query("SELECT * from ${Constants.TAG_ENTITY}")
    fun getAllTags() : List<Tag>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTags(tags: List<Tag>)

    @Delete(entity = Tag::class)
    suspend fun deleteTag(tag: Tag)
}