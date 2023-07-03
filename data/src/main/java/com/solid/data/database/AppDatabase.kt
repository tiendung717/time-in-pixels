package com.solid.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solid.data.database.converter.DateConverter
import com.solid.data.database.converter.MoodConverter
import com.solid.data.database.dao.DaoPixel
import com.solid.data.database.entity.EntityPixel


@Database(
    version = 1,
    entities = [
        EntityPixel::class
    ]
)
@TypeConverters(
    DateConverter::class,
    MoodConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "pixel1.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun daoPixel(): DaoPixel
}