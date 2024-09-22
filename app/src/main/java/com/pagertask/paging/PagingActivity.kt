package com.pagertask.paging

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pagertask.R
import com.pagertask.databinding.ActivityMainBinding
import com.pagertask.view.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
   lateinit var adapter: PagingPostdataAdapter
   lateinit var viewModel: PagingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this).get(PagingViewModel::class.java)

        adapter= PagingPostdataAdapter()
      var  layouManager= LinearLayoutManager(this)
        binding.rvUsers.layoutManager=layouManager
        binding.rvUsers.adapter=adapter
        viewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle,it)
        })
    }
}