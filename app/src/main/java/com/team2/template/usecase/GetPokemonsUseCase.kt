package com.team2.template.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.team2.template.model.Pokemon
import com.team2.template.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPokemonsUseCase {
    fun getPokemons(): Flow<PagingData<Pokemon>>
}

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class GetPokemonsUseCaseImpl @Inject constructor(
    private val repo: PokemonRepository
) : GetPokemonsUseCase {

    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return repo.getAllPokemons()
    }
}