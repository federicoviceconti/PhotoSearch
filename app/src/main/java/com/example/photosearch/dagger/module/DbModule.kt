package com.example.photosearch.dagger.module

import android.app.Application
import androidx.annotation.NonNull
import dagger.Module
import androidx.room.Room
import com.example.photosearch.database.PhotoDao
import com.example.photosearch.database.PhotoSearchRoomDatabase
import com.example.photosearch.database.TagDao
import javax.inject.Singleton
import dagger.Provides



@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): PhotoSearchRoomDatabase {
        return Room.databaseBuilder(application, PhotoSearchRoomDatabase::class.java, "photosearch.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(appDatabase: PhotoSearchRoomDatabase): PhotoDao {
        return appDatabase.photoDao()
    }

    @Provides
    @Singleton
    fun provideTagDao(appDatabase: PhotoSearchRoomDatabase): TagDao {
        return appDatabase.tagDao()
    }
}