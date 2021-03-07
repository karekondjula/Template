package com.team2.template.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)