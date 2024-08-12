package com.pagertask.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.pagertask.utils.SortBy
import com.pagertask.viewmodel.UserViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener {

    lateinit var viewmodel: UserViewmodel
    lateinit var binding: ActivityMainBinding
lateinit var layouManager:LinearLayoutManager
lateinit var adapter: UsersAdapter
var myList=ArrayList<Users>()
    var total=0
    var list= arrayListOf(SortBy.NONE, SortBy.GENDER,SortBy.NAME)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel=ViewModelProvider(this).get(UserViewmodel::class.java)
        setupSpinnerAdapter()
        setupAdapter()
        attachObserver()
        scrollDataLoadMore()
//https://dummyjson.com/users?limit=5&skip=10
    }

    private fun setupSpinnerAdapter() {
        var ad =  ArrayAdapter(
                this,
        android.R.layout.simple_spinner_item,
        list);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        binding.spinner.setAdapter(ad);
        binding.spinner. setOnItemSelectedListener(this);
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

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("222","~~~~~pos~~~"+list.get(p2))
        if(list.get(p2).toString().contentEquals("Gender",true))
            filterData(list.get(p2))
    }

    private fun filterData(get: SortBy) {
        Log.d("222","~~get~~"+get)
        var myList_=ArrayList<Users>()
        for( i in 0.. myList.size-1){
            if(get.toString().contentEquals ("Gender",true)){
                if(myList.get(i).gender.toString().equals("female"))
                    myList_.add(myList.get(i))
            }


        viewmodel.errorLiveData.observe(this, Observer {
              Log.d("222","~~errorLiveData~~~"+it)
        })


        }

        adapter= UsersAdapter(myList_)
        binding.rvUsers.adapter=adapter
adapter.notifyDataSetChanged()
        Log.d("222","~~myList_~~"+myList_.size)

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}