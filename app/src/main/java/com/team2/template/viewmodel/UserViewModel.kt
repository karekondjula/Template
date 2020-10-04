package com.team2.template.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.team2.template.model.PokemonsResult
import com.team2.template.usecase.GetPokemonsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class UserViewModel @ViewModelInject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _data = MutableLiveData<PokemonsResult>()
    val data: LiveData<PokemonsResult>
        get() = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getPokemonsUseCase.getPokemons()
                .onStart { _loadingState.postValue(LoadingState.LOADING) }
                .catch {
                    // TODO add error message to PokemonsResult
//                    _loadingState.postValue(LoadingState.error(pokemonResultList.errorBody().toString()))
                }.collect { pokemonResultList ->
                    _data.value = pokemonResultList
                }

        }
    }
}

data class LoadingState constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}