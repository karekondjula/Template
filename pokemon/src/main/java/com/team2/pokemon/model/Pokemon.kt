package com.team2.pokemon.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "pokemon")
data class Pokemon(
    @field:Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @field:Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String,

    @field:Json(name = "sprites")
    @ColumnInfo(name = "image")
    val sprites: Sprites,
)

class Sprites(@field:Json(name = "front_default") val image: String)