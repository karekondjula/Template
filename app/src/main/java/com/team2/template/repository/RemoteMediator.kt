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
//            Log.d("/// loadType", "--------------------------------------")
//            Log.d("/// loadType", "$loadType")
//            Log.d("/// state.lastItem", "${state.lastItemOrNull()}")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    pokemonDatabase.pokemonKeysDao().getPokemonKeys().firstOrNull()?.next
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

//            Log.d("/// loadKey", "$loadKey")
            val response = if (loadKey == null)
                pokemonApi.getListOfPokemons(limit = state.config.pageSize)
            else
                pokemonApi.getListOfPokemonsByUrl(loadKey)


            if (response.results.isNotEmpty()) {
                val pokemons = response.results.map{ p -> pokemonApi.getPokemonByUrl(p.url)}

                pokemonDatabase.withTransaction {
                    pokemonDatabase.pokemonsDao().savePokemons(pokemons)
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