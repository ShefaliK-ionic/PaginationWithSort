package com.pagertask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pagertask.listener.ApiListener
import com.pagertask.model.UsersResponse
import com.pagertask.view.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewmodel @Inject constructor(private val usersRepository: UsersRepository):ViewModel(){

    var mutableLiveData=MutableLiveData<UsersResponse>()
    var errorLiveData=MutableLiveData<String>()
    var page=0
    var limit=30
    var skip=-30

//https://dummyjson.com/users?limit=5&skip=10
    init {


        callApi()
    }

     fun callApi() {
         val data = HashMap<String,Int>()
         page=page+10
         skip=skip+30
         data.put("page",page)
         data.put("limit",limit)
         data.put("skip",skip)
         usersRepository.getUsers(queryMap =data, apiListener = object :ApiListener{
            override fun onSuccess(obj: Object) {
                mutableLiveData.postValue(obj as UsersResponse)
            }

            override fun onError(error: String) {

            }

        } )
    }


}