package com.pagertask.listener

import java.lang.Error
import java.util.Objects

interface ApiListener {

    fun onSuccess(obj:Object)
    fun onError(error: String)

}