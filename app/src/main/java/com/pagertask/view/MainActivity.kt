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
import java.util.Collections


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener {

    lateinit var viewmodel: UserViewmodel
    lateinit var binding: ActivityMainBinding
lateinit var layouManager:LinearLayoutManager
lateinit var adapter: UsersAdapter

var filteredList=ArrayList<Users>()
    var total=0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sumFun()
//        viewmodel=ViewModelProvider(this).get(UserViewmodel::class.java)
//        setupSpinnerAdapter()
//        setupAdapter()
//        attachObserver()
//        scrollDataLoadMore()
//https://dummyjson.com/users?limit=5&skip=10
    }

    private fun sumFun() {
        var list= arrayListOf(2,1,3)
        for(i in 0..list.size-1){
            for(k in i+1..list.size-1) {

                if(list.get(i)>list.get(k)){
                    var big=list.get(i)
                    list.set(i,list.get(k))
                    list.set(k,big)

                }
            }

            if(list.size>2) {
                var sumWithLow=0
                for (i in 0..list.size - 2) {
                     sumWithLow+=list.get(i)
                }
                Log.d("222","~~sumWithLow~"+sumWithLow)

            }


            if(list.size>2) {
                var sumWithHigh=0
                for (i in 1..list.size - 1) {
                    sumWithHigh+=list.get(i)
                }
                Log.d("222","~~sumWithHigh~"+sumWithHigh)

            }


            Log.d("222","~~sum~"+Gson().toJson(list))
            }
    }

    lateinit var ad:ArrayAdapter<SortBy>
    private fun setupSpinnerAdapter() {
       ad =  ArrayAdapter(
                this,
        android.R.layout.simple_spinner_item,
           viewmodel.spinnerList);

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
                    if(total > viewmodel.myList.size){
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

            it.total?.let {
                total=it
            }
        if(it.users.size>0 && viewmodel.  myList.size < total) {

            viewmodel.  myList.addAll(it.users)
            filterData(binding.spinner.selectedItem.toString())

        }




        })

        viewmodel.errorLiveData.observe(this, Observer {
            Log.d("222","~~errorLiveData~~~"+it)
        })

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("222","~~~~~pos~~~"+viewmodel.spinnerList.get(p2))
//        if(spinnerList.get(p2).toString().contentEquals("Gender",true))
            filterData(viewmodel.spinnerList.get(p2).toString())
    }

    private fun filterData(get: String) {
        Log.d("222","~~get~878~"+viewmodel.myList.size+"~~total~"+total)
//        var myList_=ArrayList<Users>()
        if(filteredList.size < total) {
            filteredList.clear()
        }
        if(get.toString().contentEquals ("None",true)) {
            filteredList.addAll(viewmodel.myList)

        }else if(get.toString().contentEquals ("Gender",true)){

        for( i in 0.. viewmodel.myList.size-1) {

            if (viewmodel.myList.get(i).gender.toString().equals("female"))
                filteredList.add(viewmodel.myList.get(i))
        } }else if(get.toString().contentEquals ("Name",true)) {
            Log.d("222", "~~~localNameList~~99~~~~")

            var localNameList = ArrayList<Users>()
            localNameList.addAll(viewmodel.myList)

            Log.d("222", "~~~localNameList~~~~~~")

            Collections.sort(localNameList,
                Comparator<Users?> { object1, object2 ->
                    object1?.username!!.compareTo(object2!!.username+"")
                })
//                if (localNameList.get(i).username.toString().get(0).code > (localNameList.get(j).username.toString().get(0).code)){
//                    var big=localNameList.get(i)
//
//                    localNameList.set(i,localNameList.get(j))
//                    localNameList.set(j,big)
//                    Log.d("222","~~~localNameList~~SET~~~~")
//
//                }                }


            filteredList.addAll(localNameList)
        }


//        adapter= UsersAdapter(filteredList)
//        binding.rvUsers.adapter=adapter
        adapter.notifyDataSetChanged()
        Log.d("222","~~myList_~~"+filteredList.size+"~~~"+viewmodel.myList.size)

}

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}