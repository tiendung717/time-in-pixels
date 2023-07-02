package com.solid.data.domain

import org.threeten.bp.LocalDate

data class Pixel(
    val date: LocalDate,
    val note: String,
    val mood: Mood
)