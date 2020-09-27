package com.team2.template.repository

import com.team2.template.service.PokemonApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: PokemonApi) {
    fun getAllPokemons() = api.getListOfPokemons()
}