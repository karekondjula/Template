package com.team2.template.model

import androidx.room.ColumnInfo

data class PokemonsResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "name") val name: String,
)