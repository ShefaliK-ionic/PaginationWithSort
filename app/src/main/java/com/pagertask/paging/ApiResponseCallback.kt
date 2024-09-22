package com.pagertask.paging

sealed class ApiResponseCallback<T> {

    class Failed<T>(val message: String) : ApiResponseCallback<T>()
    class Loading<T> : ApiResponseCallback<T>()
    class Success<T>(val data: T) : ApiResponseCallback<T>()
    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}
