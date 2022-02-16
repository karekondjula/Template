package com.team2.pokemon.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.team2.pokemon.model.Pokemon
import com.team2.pokemon.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface GetPokemonsUseCase {
    fun getPokemons(): Flow<PagingData<Pokemon>>
}

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class GetPokemonsUseCaseImpl constructor(
    private val repo: PokemonRepository
) : GetPokemonsUseCase {

    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return repo.getAllPokemons()
    }
}