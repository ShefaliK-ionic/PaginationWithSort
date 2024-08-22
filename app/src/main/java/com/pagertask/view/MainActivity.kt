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
import com.google.gson.Gson
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
var filteredList=ArrayList<Users>()
    var total=0
    var spinnerList= arrayListOf(SortBy.NONE, SortBy.GENDER,SortBy.NAME)

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
   lateinit var ad:ArrayAdapter<SortBy>
    private fun setupSpinnerAdapter() {
       ad =  ArrayAdapter(
                this,
        android.R.layout.simple_spinner_item,
        spinnerList);

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
        adapter= UsersAdapter(filteredList)
        binding.rvUsers.adapter=adapter
    }

    private fun attachObserver() {

        viewmodel.mutableLiveData.observe(this, Observer {
            Log.d("222","~~mutableLiveData~~~"+Gson().toJson(it))
            Log.d("222","~~adapterSelectedposiation~~~"+binding.spinner.selectedItem.toString())
         myList.addAll(it.users)
           filterData(binding.spinner.selectedItem.toString())


            it.total?.let {
                total=it
            }

            adapter.notifyDataSetChanged()
        })

        viewmodel.errorLiveData.observe(this, Observer {
            Log.d("222","~~errorLiveData~~~"+it)
        })

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("222","~~~~~pos~~~"+spinnerList.get(p2))
        if(spinnerList.get(p2).toString().contentEquals("Gender",true))
            filterData(spinnerList.get(p2).toString())
    }

    private fun filterData(get: String) {
        Log.d("222","~~get~~"+get)
//        var myList_=ArrayList<Users>()
        filteredList.clear()
        if(get.toString().contentEquals ("None",true)) {
            filteredList.addAll(myList)

        }else if(get.toString().contentEquals ("Gender",true)){

        for( i in 0.. myList.size-1) {

            if (myList.get(i).gender.toString().equals("female"))
                filteredList.add(myList.get(i))
        } }else if(get.toString().contentEquals ("Name",true)) {


            for( i in 0.. myList.size-1) {

                if (myList.get(i).username.toString().equals("female"))
                    filteredList.add(myList.get(i))
            }


        }


//        adapter= UsersAdapter(filteredList)
//        binding.rvUsers.adapter=adapter
        adapter.notifyDataSetChanged()
        Log.d("222","~~myList_~~"+filteredList.size)

}

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}