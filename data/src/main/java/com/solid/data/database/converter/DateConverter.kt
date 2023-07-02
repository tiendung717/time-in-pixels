package com.solid.data.database.converter

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import java.sql.Timestamp

class DateConverter {
    @TypeConverter
    fun fromDate(date: LocalDate) : Long {
        return 0L
    }

    @TypeConverter
    fun toDate(timestamp: Long) : LocalDate {
        return LocalDate.now()
    }
}