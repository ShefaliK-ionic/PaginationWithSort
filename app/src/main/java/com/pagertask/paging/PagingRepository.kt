package com.pagertask.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.pagertask.model.Users
import com.pagertask.network.ApiInterface
import javax.inject.Inject

class PagingRepository @Inject constructor(val apiInterface: ApiInterface) {

    fun getPost()= Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {PostPagingDataSource(apiInterface)}
    ).liveData




}