package com.team2.template.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.team2.template.database.PokemonDatabase
import com.team2.template.model.Pokemon
import com.team2.template.service.PokemonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PokemonRepository @Inject constructor(
    private val api: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) {

    fun getAllPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 25,
            ),
            remoteMediator = PokemonRemoteMediator(
                pokemonApi = api,
                pokemonDatabase = pokemonDatabase
            ),
            pagingSourceFactory = { pokemonDatabase.pokemonsDao().getPokemons() }
        ).flow
    }
}