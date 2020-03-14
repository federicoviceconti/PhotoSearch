package com.example.photosearch.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photosearch.core.Constants

@Entity(tableName = Constants.TAG_ENTITY)
data class Tag(@PrimaryKey val name: String, var isSelected: Boolean)