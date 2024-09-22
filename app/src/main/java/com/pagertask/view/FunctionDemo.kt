package com.pagertask.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pagertask.R

class FunctionDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_function_demo)

       defaultArgumentFun("Shriti",20)
        defaultArgumentFun()
        namedFunWithDefault(age=10)
//        defaultArgumentFun(20)
    }

    private fun defaultArgumentFun(name:String="Devoy",age:Int=25) {
        Log.d("222", "defaultArgumentFun: "+name+"~~~"+age)
    }

    private fun namedFunWithDefault(name:String="duck",age:Int=25) {
        Log.d("222", "defaultArgumentFun: "+name+"~~~"+age)
    }
}