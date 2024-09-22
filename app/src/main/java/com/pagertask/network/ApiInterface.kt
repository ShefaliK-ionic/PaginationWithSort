package com.pagertask.network

import com.pagertask.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {


    @GET("users")
     fun getUsers(@QueryMap  queryMap: HashMap<String, Int>): Call<UsersResponse>

    @GET("users")
   suspend fun getUsersPaging(@QueryMap  queryMap: HashMap<String, Int>): UsersResponse


}