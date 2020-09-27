package com.team2.template.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.team2.template.R
import com.team2.template.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.data.observe(viewLifecycleOwner, Observer {
            it.results.forEach { user -> Log.d(">>>", user.url) }
        })

        userViewModel.loadingState.observe(viewLifecycleOwner, Observer {
             Log.d(">>> state", it.toString())
        })
    }
}