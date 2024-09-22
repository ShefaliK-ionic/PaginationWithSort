package com.pagertask.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pagertask.model.Users
import com.pagertask.network.ApiInterface
import javax.inject.Inject

class PostPagingDataSource @Inject constructor (val apiInterface: ApiInterface):PagingSource<Int, Users>() {
    override fun getRefreshKey(state: PagingState<Int, Users>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
    var skip=-30
    var page=0
    var limit=30

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Users> {

        return try {
            val position = params.key ?: 1
            val data = HashMap<String,Int>()
             page=position+10
            Log.d("222", "load: "+position)
             skip=skip+position
            data.put("page",page)
            data.put("limit",limit)
            data.put("skip",skip)

            val response = apiInterface.getUsersPaging(data)

            if (response.users != null) {
                LoadResult.Page(
                    data = response.users,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (position == response.total) null else (position + 1)
                )
            } else {
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}