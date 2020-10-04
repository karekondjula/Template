package com.team2.template.usecase

import com.team2.template.model.PokemonsResult
import com.team2.template.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPokemonsUseCase {
    suspend fun getPokemons(): Flow<PokemonsResult>
}

@ExperimentalCoroutinesApi
class GetPokemonsUseCaseImpl @Inject constructor(
    private val repo: PokemonRepository
) : GetPokemonsUseCase {

    override suspend fun getPokemons(): Flow<PokemonsResult> {
        return repo.getAllPokemons()
    }
}