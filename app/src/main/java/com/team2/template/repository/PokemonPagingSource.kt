package com.team2.template.repository

import androidx.paging.PagingSource
import com.team2.template.model.Pokemon
import com.team2.template.service.PokemonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PokemonPagingSource(private val pokemonApi: PokemonApi) :
    PagingSource<String, Pokemon>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Pokemon> {
        return try {
            val result =
                if (params.key?.isNotEmpty() != null) {
                    pokemonApi.getListOfPokemonsByUrl(params.key)
                } else {
                    pokemonApi.getListOfPokemons(params.pageSize)
                }

            LoadResult.Page(
                result.results,
                result.previous,
                result.next
            )
        } catch (e: Exception) {
//            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}