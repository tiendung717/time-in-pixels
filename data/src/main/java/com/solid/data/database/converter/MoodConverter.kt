package com.solid.data.database.converter

import androidx.room.TypeConverter
import com.solid.data.domain.Mood

class MoodConverter {

    @TypeConverter
    fun toMood(value: Int) : Mood {
        return Mood.fromValue(value)
    }

    @TypeConverter
    fun fromMood(mood: Mood) : Int {
        return mood.value
    }
}