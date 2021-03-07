package com.team2.template.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.team2.template.model.Pokemon

@Dao
interface PokemonsDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePokemons(pokemonsList: List<Pokemon>)

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): PagingSource<Int, Pokemon>
}