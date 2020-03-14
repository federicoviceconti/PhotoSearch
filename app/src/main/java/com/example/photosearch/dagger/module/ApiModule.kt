package com.example.photosearch.dagger.module

import com.example.photosearch.services.UnsplashService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit




@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideJackson(): JacksonConverterFactory = JacksonConverterFactory.create()

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun callAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(callAdapter: RxJava2CallAdapterFactory,
                        jackson: JacksonConverterFactory,
                        okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(callAdapter)
            .addConverterFactory(jackson)
            .baseUrl(UnsplashService.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideUnsplashService(retrofit: Retrofit): UnsplashService {
        return retrofit.create<UnsplashService>(UnsplashService::class.java)
    }
}