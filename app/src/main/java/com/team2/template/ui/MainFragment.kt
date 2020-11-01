package com.team2.template.ui

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.location.ActivityTransitionResult
import com.team2.template.R
import com.team2.template.adapter.LoadingStateAdapter
import com.team2.template.adapter.PokemonPagingAdapter
import com.team2.template.databinding.FragmentMainBinding
import com.team2.template.service.BackgroundDetectedActivitiesService
import com.team2.template.viewmodel.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        val INTENT_ACTION = "BROADCAST_DETECTED_ACTIVITY"
    }

    private val pokemonsViewModel: PokemonsViewModel by viewModels()
    private val pagingAdapter = PokemonPagingAdapter()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

//    inner class MyReceiver : BroadcastReceiver() {
//
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (ActivityTransitionResult.hasResult(intent)) {
//                ActivityTransitionResult.extractResult(intent)?.let {
//                    processTransitionResult(it)
//                }
//            } else {
////                txt_activity.text = "-100"
//            }
//        }
//
//        fun processTransitionResult(result: ActivityTransitionResult) {
//            for (event in result.transitionEvents) {
//                onDetectedTransitionEvent(event)
//            }
//        }
//
//        private fun onDetectedTransitionEvent(activity: ActivityTransitionEvent) {
//            txt_activity.text = activity.activityType.toString()
//            when (activity.activityType) {
//                DetectedActivity.STILL,
//                DetectedActivity.WALKING -> {
////                        txt_confidence.text = activity.confidence.toString()
//                }
//                else -> {
//                }
//            }
//        }
//    }
//
//    val receiver = MyReceiver()

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

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
        list.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { pagingAdapter.retry() },
            footer = LoadingStateAdapter { pagingAdapter.retry() }
        )
        list.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenCreated {
            pokemonsViewModel.fetchPokemons().collectLatest {
                pagingAdapter.submitData(it)
            }
        }

//        btn_start_tracking.setOnClickListener { startTracking() }
//        btn_stop_tracking.setOnClickListener { stopTracking() }
//
//        //////////////////////////////////////////////////////////////////////////
//        val mIntentService = Intent(INTENT_ACTION)
////        val mPendingIntent = PendingIntent.getService(
////            requireContext(),
////            1,
////            mIntentService,
////            PendingIntent.FLAG_UPDATE_CURRENT
////        )
//        val mPendingIntent = PendingIntent.getBroadcast(
//            requireContext(),
//            1,
//            mIntentService,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val transitions = mutableListOf<ActivityTransition>()
//        transitions +=
//            ActivityTransition.Builder()
//                .setActivityType(DetectedActivity.STILL)
//                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
//                .build()
//        transitions += ActivityTransition.Builder()
//            .setActivityType(DetectedActivity.STILL)
//            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
//            .build()
//        transitions +=
//            ActivityTransition.Builder()
//                .setActivityType(DetectedActivity.WALKING)
//                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
//                .build()
//        transitions +=
//            ActivityTransition.Builder()
//                .setActivityType(DetectedActivity.WALKING)
//                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
//                .build()
//
//        val request = ActivityTransitionRequest(transitions)
//
//        val task = ActivityRecognition.getClient(requireContext())
//            .requestActivityTransitionUpdates(request, mPendingIntent)
//
//        task?.addOnSuccessListener {
//            Toast.makeText(
//                context,
//                "Successfully requested activity updates",
//                Toast.LENGTH_SHORT
//            ).show()
//            val intent = Intent(INTENT_ACTION)
//            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//        }
//        task?.addOnFailureListener {
//            Toast.makeText(
//                context,
//                it.message,
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        //////////////////////////////////////////////////////////////////

//        val mActivityRecognitionClient = ActivityRecognitionClient(requireContext())
//        val mIntentService = Intent(requireContext(), MyReceiver::class.java)
//        val mPendingIntent = PendingIntent.getBroadcast(
//            requireContext(),
//            1,
//            mIntentService,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val task = mActivityRecognitionClient.requestActivityUpdates(
//            BackgroundDetectedActivitiesService.DETECTION_INTERVAL_IN_MILLISECONDS,
//            mPendingIntent
//        )
//        task?.addOnSuccessListener {
//            Toast.makeText(
//                requireContext(),
//                "Successfully requested activity updates",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        task?.addOnFailureListener {
//            Toast.makeText(
//                requireContext(),
//                it.message,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

//    override fun onStart() {
//        super.onStart()
//
//        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
//            receiver,
//            IntentFilter(INTENT_ACTION)
//        )
//    }

//    private fun startTracking() {
//        val intent = Intent(requireActivity(), BackgroundDetectedActivitiesService::class.java)
//        requireActivity().startService(intent)
//    }
//
//    private fun stopTracking() {
//        val intent = Intent(requireActivity(), BackgroundDetectedActivitiesService::class.java)
//        requireActivity().stopService(intent)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
//    }
}