package com.pagertask.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.gson.Gson
import com.pagertask.R
import com.pagertask.databinding.ActivityMainBinding
import com.pagertask.model.Users
import com.pagertask.viewmodel.UserViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewmodel: UserViewmodel
    lateinit var binding: ActivityMainBinding
lateinit var layouManager:LinearLayoutManager
lateinit var adapter: UsersAdapter
var myList=ArrayList<Users>()
    var total=0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel=ViewModelProvider(this).get(UserViewmodel::class.java)
        setupAdapter()
        attachObserver()
        scrollDataLoadMore()
//https://dummyjson.com/users?limit=5&skip=10
    }

    private fun scrollDataLoadMore() {


        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                Log.d("222","~pag/e~~"+viewmodel.page)

                if(!binding.rvUsers.canScrollVertically(1)/*layouManager.findLastVisibleItemPosition() == myList.size-1*/){
                    Log.d("222","~page~8~"+viewmodel.page)
                    if(total > myList.size){
                        viewmodel.callApi()
                    }else{
                        Toast.makeText(this@MainActivity,"Reached last",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun setupAdapter() {
        layouManager=LinearLayoutManager(this)
        binding.rvUsers.layoutManager=layouManager
        adapter= UsersAdapter(myList)
        binding.rvUsers.adapter=adapter
    }

    private fun attachObserver() {

        viewmodel.mutableLiveData.observe(this, Observer {
            Log.d("222","~~mutableLiveData~~~"+Gson().toJson(it))
         myList.addAll(it.users)
            it.total?.let {
                total=it
            }
            adapter.notifyDataSetChanged()
        })


        viewmodel.errorLiveData.observe(this, Observer {
              Log.d("222","~~errorLiveData~~~"+it)
        })

    }

}