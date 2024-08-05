package com.pagertask.view

import androidx.lifecycle.MutableLiveData
import com.pagertask.listener.ApiListener
import com.pagertask.model.UsersResponse
import com.pagertask.network.ApiInterface
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.QueryMap
import javax.inject.Inject
import javax.security.auth.callback.Callback


class UsersRepository @Inject constructor (val apiInterface: ApiInterface) {


    fun getUsers(queryMap: HashMap<String,Int>,apiListener: ApiListener){

        val call= apiInterface.getUsers(queryMap);
        call.enqueue(object : retrofit2.Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if(response.isSuccessful){
                    apiListener.onSuccess(response.body() as Object)

                }else{
                    apiListener.onError(response.message())
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {

            }


        })




    }


}