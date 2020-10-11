package com.team2.template.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.team2.template.R
import com.team2.template.databinding.FragmentMainBinding
import com.team2.template.service.BackgroundDetectedActivitiesService
import com.team2.template.viewmodel.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val pokemonsViewModel: PokemonsViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonsViewModel.data
            .onEach { pokemonResult -> Log.d(">>>", pokemonResult?.results.toString()) }
            .launchIn(lifecycleScope)

        pokemonsViewModel.loadingState
            .onEach { Log.d(">>> state", it.toString()) }
            .launchIn(lifecycleScope)

//        btn_start_tracking.setOnClickListener { startTracking() }
//        btn_stop_tracking.setOnClickListener { stopTracking() }
    }

//    private fun startTracking() {
//        val intent = Intent(requireActivity(), BackgroundDetectedActivitiesService::class.java)
//        requireActivity().startService(intent)
//    }
//
//    private fun stopTracking() {
//        val intent = Intent(requireActivity(), BackgroundDetectedActivitiesService::class.java)
//        requireActivity().stopService(intent)
//    }
}