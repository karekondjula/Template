package com.team2.template.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.team2.template.model.Pokemon
import com.team2.template.usecase.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    fun fetchPokemons(): Flow<PagingData<Pokemon>> {
        return getPokemonsUseCase.getPokemons().cachedIn(viewModelScope)
    }
}