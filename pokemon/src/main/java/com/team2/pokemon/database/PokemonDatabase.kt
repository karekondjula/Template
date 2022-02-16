package com.team2.pokemon.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.team2.pokemon.model.Pokemon
import com.team2.pokemon.model.PokemonKeys

@Database(
    entities = [Pokemon::class, PokemonKeys::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonsDao(): PokemonsDao
}