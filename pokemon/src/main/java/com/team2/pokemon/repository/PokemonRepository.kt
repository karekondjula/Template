package com.team2.pokemon.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.team2.pokemon.database.PokemonDatabase
import com.team2.pokemon.database.PokemonsDao
import com.team2.pokemon.model.Pokemon
import com.team2.pokemon.service.PokemonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PokemonRepository constructor(
    private val api: PokemonApi,
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonsDao: PokemonsDao,
) {

    fun getAllPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            PagingConfig(
                pageSize = 100,
                enablePlaceholders = false,
                prefetchDistance = 120,
            ),
            remoteMediator = PokemonRemoteMediator(
                pokemonApi = api,
                pokemonDatabase = pokemonDatabase,
                pokemonsDao = pokemonsDao,
            ),
            pagingSourceFactory = { pokemonsDao.getPokemons() }
        ).flow
    }
}