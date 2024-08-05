package com.pagertask.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.pagertask.R
import com.pagertask.databinding.UsersItemBinding
import com.pagertask.model.Users

class UsersAdapter(var myList:ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding=UsersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.usersItemBinding.tvUsername.setText(""+myList.get(position).username+" "+myList.get(position).id)
        holder.usersItemBinding.tvEmail.setText(""+myList.get(position).email)
        myList.get(position).image?.let {
            Glide
                .with(holder.usersItemBinding.tvEmail.context)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.usersItemBinding.imgProfile);
        }


    }


    inner class MyViewHolder(val usersItemBinding:UsersItemBinding):
    RecyclerView.ViewHolder(usersItemBinding.root)
}