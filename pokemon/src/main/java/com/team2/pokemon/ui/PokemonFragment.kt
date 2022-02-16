package com.team2.pokemon.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.team2.pokemon.R
import com.team2.pokemon.adapter.LoadingStateAdapter
import com.team2.pokemon.adapter.PokemonPagingAdapter
import com.team2.pokemon.databinding.FragmentPokemonBinding
import com.team2.pokemon.viewmodel.PokemonsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PokemonFragment : Fragment(R.layout.fragment_pokemon) {

    private val pokemonsViewModel: PokemonsViewModel by viewModel()
    private val pagingAdapter = PokemonPagingAdapter()

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)

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

        lifecycleScope.launchWhenStarted {
            pokemonsViewModel.fetchPokemons().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}