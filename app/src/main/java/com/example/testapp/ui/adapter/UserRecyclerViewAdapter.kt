package com.example.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

import com.example.testapp.R
import com.example.testapp.data.model.User

class UserRecyclerViewAdapter(
    private val userList: ArrayList<User>,

    ) : RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(user = userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.textViewUserName)
        val email: TextView = itemView.findViewById(R.id.textViewUserEmail)
        val userImage: ImageView = itemView.findViewById(R.id.imageViewAvatar)
        fun bind(user: User) {
            username.text = user.name
            email.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .transition(withCrossFade())
                .placeholder(R.drawable.placeholder)
                .into(userImage)
        }

    }

    fun addData(list: List<User>) {
        userList.addAll(list)
    }


}