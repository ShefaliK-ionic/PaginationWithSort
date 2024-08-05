package com.pagertask.di

import com.pagertask.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
//https://dummyjson.com/users?limit=5&skip=10
    @Provides
    fun getBaseUrl()="https://dummyjson.com/"

    @Provides
    @Singleton
    fun getApiService(baseUrl:String):ApiInterface=
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build().
        create(ApiInterface::class.java)


}