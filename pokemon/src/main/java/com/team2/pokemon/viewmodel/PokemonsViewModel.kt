package com.team2.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.team2.pokemon.model.Pokemon
import com.team2.pokemon.usecase.GetPokemonsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PokemonsViewModel constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    fun fetchPokemons(): Flow<PagingData<Pokemon>> {
        return getPokemonsUseCase.getPokemons().cachedIn(viewModelScope)
    }
}