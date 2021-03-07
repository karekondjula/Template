package com.team2.template.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team2.template.model.Pokemon
import com.team2.template.model.PokemonKeys

@Database(
    entities = arrayOf(Pokemon::class, PokemonKeys::class),
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonsDao(): PokemonsDao
    abstract fun pokemonKeysDao(): PokemonKeysDao
}