package com.team2.template.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_keys")
data class PokemonKeys(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "previous") val previous: String?,
    @ColumnInfo(name = "next") val next: String?
)