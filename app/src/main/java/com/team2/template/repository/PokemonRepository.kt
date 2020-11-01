package com.team2.template.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.team2.template.model.Pokemon
import com.team2.template.service.PokemonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PokemonRepository @Inject constructor(private val api: PokemonApi) {

    fun getAllPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(PagingConfig(pageSize = 10, enablePlaceholders = false)) {
            PokemonPagingSource(api)
        }.flow
    }
}