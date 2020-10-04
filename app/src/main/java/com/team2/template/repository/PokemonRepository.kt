package com.team2.template.repository

import com.team2.template.model.PokemonsResult
import com.team2.template.service.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PokemonRepository @Inject constructor(private val api: PokemonApi) {
    fun getAllPokemons(): Flow<PokemonsResult> {
        return flow {
            val response = api.getListOfPokemons()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}