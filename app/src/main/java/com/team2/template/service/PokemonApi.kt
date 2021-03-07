package com.team2.template.service

import com.team2.template.model.Pokemon
import com.team2.template.model.PokemonsResult
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {

    @GET("pokemon")
    suspend fun getListOfPokemons(@Query("limit") limit: Int = 50): PokemonsResult

    @GET
    suspend fun getListOfPokemonsByUrl(@Url url: String?): PokemonsResult

    @GET
    suspend fun getPokemonByUrl(@Url url: String?): Pokemon
}