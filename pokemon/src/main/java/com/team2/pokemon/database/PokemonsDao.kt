package com.team2.pokemon.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.team2.pokemon.model.Pokemon
import com.team2.pokemon.model.PokemonKeys

@Dao
interface PokemonsDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePokemons(pokemonsList: List<Pokemon>)

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): PagingSource<Int, Pokemon>

    @Insert(onConflict = REPLACE)
    suspend fun savePokemonKeys(pokemonKeys: PokemonKeys)

    @Query("SELECT * FROM pokemon_keys ORDER BY id DESC")
    suspend fun getPokemonKeys(): List<PokemonKeys>
}