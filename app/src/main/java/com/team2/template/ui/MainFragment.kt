package com.team2.template.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.team2.template.R
import com.team2.template.adapter.LoadingStateAdapter
import com.team2.template.adapter.PokemonPagingAdapter
import com.team2.template.databinding.FragmentMainBinding
import com.team2.template.viewmodel.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val pokemonsViewModel: PokemonsViewModel by viewModels()
    private val pagingAdapter = PokemonPagingAdapter()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
        binding.list.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { pagingAdapter.retry() },
            footer = LoadingStateAdapter { pagingAdapter.retry() }
        )
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenCreated {
            pokemonsViewModel.fetchPokemons().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}