package com.team2.template.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team2.template.R

class MainActivity : AppCompatActivity() {

//    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        userViewModel.data.observe(this, Observer {
//            it.forEach { user -> Log.d(">>>", user.avatar_url) }
//        })
//
//        userViewModel.loadingState.observe(this, Observer {
//             Log.d(">>> state", it.toString())
//        })
    }
}