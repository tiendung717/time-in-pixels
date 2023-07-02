package com.solid.data.domain

import androidx.annotation.StringRes
import com.solid.data.R
import java.lang.IllegalArgumentException

open class Mood(val value: Int, @StringRes val name: Int) {
    companion object {
        fun fromValue(value: Int) : Mood {
            return when (value) {
                0 -> Negative
                1 -> Positive
                else -> throw IllegalArgumentException("Cannot parse $value to Mood!")
            }
        }
    }
    object Negative: Mood(0, R.string.mood_negative)
    object Positive: Mood(1, R.string.mood_positive)
}