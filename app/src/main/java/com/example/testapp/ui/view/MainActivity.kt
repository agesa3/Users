package com.example.testapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.data.model.User
import com.example.testapp.ui.adapter.UserRecyclerViewAdapter
import com.example.testapp.ui.viewmodel.MainViewModel
import com.example.testapp.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter
    private lateinit var userListRecyclerview: RecyclerView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        setUpRecyclerView()
        setUpObserver()
    }


    private fun setUpRecyclerView() {

        userListRecyclerview = findViewById(R.id.userRecyclerView)
        userRecyclerViewAdapter = UserRecyclerViewAdapter(arrayListOf())
        userListRecyclerview.adapter = userRecyclerViewAdapter
        userListRecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun setUpObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users ->
                        renderList(users)
                    }
                    userListRecyclerview.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    userListRecyclerview.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun renderList(users: List<User>) {
        userRecyclerViewAdapter.addData(users)
        userRecyclerViewAdapter.notifyDataSetChanged()
    }


}