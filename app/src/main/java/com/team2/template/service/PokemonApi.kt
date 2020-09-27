package com.team2.template.service

import com.team2.template.model.PokemonsResult
import retrofit2.Call
import retrofit2.http.GET

interface PokemonApi {

    @GET("pokemon?limit=20")
    fun getListOfPokemons(): Call<PokemonsResult>
}