package com.solid.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.solid.data.database.entity.EntityPixel
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
abstract class DaoPixel {

    @Query("select * from EntityPixel")
    abstract fun getAll(): Flow<List<EntityPixel>>

    @Query("select * from EntityPixel where date >= :start and date <= :end")
    abstract fun getTimeInPixel(start: LocalDate, end: LocalDate): Flow<List<EntityPixel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(entityPixel: EntityPixel)

    @Update
    abstract suspend fun update(entityPixel: EntityPixel)

    @Delete
    abstract suspend fun delete(entityPixel: EntityPixel)

    @Query("delete from EntityPixel where date = :date")
    abstract suspend fun delete(date: LocalDate)


}