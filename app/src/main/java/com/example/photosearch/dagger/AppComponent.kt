package com.example.photosearch.dagger

import android.app.Application
import com.example.photosearch.PhotoSearchApplication
import com.example.photosearch.dagger.module.ActivityModule
import com.example.photosearch.dagger.module.ApiModule
import com.example.photosearch.dagger.module.DbModule
import com.example.photosearch.dagger.module.ViewModelModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance



@Component(modules = [
    ApiModule::class,
    DbModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    AndroidSupportInjectionModule::class
])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(appController: PhotoSearchApplication)
}