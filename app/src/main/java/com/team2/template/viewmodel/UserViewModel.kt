package com.team2.template.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.team2.template.model.Pokemon
import com.team2.template.model.PokemonsResult
import com.team2.template.repository.PokemonRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel @ViewModelInject constructor(
    private val repo: PokemonRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), Callback<PokemonsResult> {

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
        _loadingState.postValue(LoadingState.LOADING)
        repo.getAllPokemons().enqueue(this)
    }

    override fun onFailure(call: Call<PokemonsResult>, t: Throwable) {
        _loadingState.postValue(LoadingState.error(t.message))
    }

    override fun onResponse(call: Call<PokemonsResult>, response: Response<PokemonsResult>) {
        if (response.isSuccessful) {
            _data.postValue(response.body())
            _loadingState.postValue(LoadingState.LOADED)
        } else {
            _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
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