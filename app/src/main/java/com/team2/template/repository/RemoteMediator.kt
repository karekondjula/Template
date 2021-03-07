package com.team2.template.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.team2.template.database.PokemonDatabase
import com.team2.template.model.Pokemon
import com.team2.template.model.PokemonKeys
import com.team2.template.service.PokemonApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PokemonRemoteMediator(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, Pokemon>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    pokemonDatabase.pokemonKeysDao().getPokemonKeys().firstOrNull()?.next
                }
            }

            val response = if (loadKey == null)
                pokemonApi.getListOfPokemons(limit = state.config.pageSize)
            else
                pokemonApi.getListOfPokemonsByUrl(loadKey)

            if (response.results.isNotEmpty()) {
                pokemonDatabase.withTransaction {
                    pokemonDatabase.pokemonsDao().savePokemons(response.results)
                    pokemonDatabase.pokemonKeysDao()
                        .savePokemonKeys(PokemonKeys(0, response.previous, response.next))
                }
            }

            MediatorResult.Success(response.next == null)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}