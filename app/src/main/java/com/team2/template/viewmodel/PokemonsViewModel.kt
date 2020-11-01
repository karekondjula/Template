package com.team2.template.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.team2.template.model.Pokemon
import com.team2.template.usecase.GetPokemonsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PokemonsViewModel @ViewModelInject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun fetchPokemons(): Flow<PagingData<Pokemon>> {
        return getPokemonsUseCase.getPokemons().cachedIn(viewModelScope)
    }
}