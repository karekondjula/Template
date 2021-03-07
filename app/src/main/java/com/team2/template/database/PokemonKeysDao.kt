package com.team2.template.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.team2.template.model.PokemonKeys

@Dao
interface PokemonKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePokemonKeys(pokemonKeys: PokemonKeys)

    @Query("SELECT * FROM pokemon_keys ORDER BY id DESC")
    suspend fun getPokemonKeys(): List<PokemonKeys>
}