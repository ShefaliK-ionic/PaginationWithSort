package com.pagertask.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pagertask.R
import com.pagertask.databinding.UsersItemBinding
import com.pagertask.model.Users

class PagingPostdataAdapter:PagingDataAdapter<Users,PagingPostdataAdapter.UserViewHolder>(comparator) {


    class UserViewHolder(val usersItemBinding:UsersItemBinding):
        RecyclerView.ViewHolder(usersItemBinding.root)

    companion object{

        private val comparator=object :DiffUtil.ItemCallback<Users>(){

            override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem.username==newItem.username
            }

            override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
               return oldItem==newItem
            }
        }

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       getItem(position)?.let { }
        holder.usersItemBinding.tvUsername.setText(
            "" + getItem(position)?.username + " " + getItem(
                position
            )?.id
        )
        holder.usersItemBinding.tvEmail.setText("" + getItem(position)?.email)
        getItem(position)?.image?.let {
            Glide
                .with(holder.usersItemBinding.tvEmail.context)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.usersItemBinding.imgProfile);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingPostdataAdapter.UserViewHolder {
        val binding=UsersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PagingPostdataAdapter.UserViewHolder(binding)
    }

}