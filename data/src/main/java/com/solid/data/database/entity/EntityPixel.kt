package com.solid.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solid.data.domain.Mood
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

@Entity
data class EntityPixel(
    @PrimaryKey
    val date: LocalDate,

    val note: String,

    val mood: Mood
)