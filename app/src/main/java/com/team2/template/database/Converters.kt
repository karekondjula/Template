package com.team2.template.database

import androidx.room.TypeConverter
import com.team2.template.model.Sprites

class Converters {
    @TypeConverter
    fun imageToSprite(value: String?): Sprites? {
        return value?.let { Sprites(it) }
    }

    @TypeConverter
    fun spritesToImage(sprites: Sprites?): String? {
        return sprites?.image
    }
}