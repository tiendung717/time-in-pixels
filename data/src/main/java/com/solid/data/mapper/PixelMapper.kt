package com.solid.data.mapper

import com.solid.data.database.entity.EntityPixel
import com.solid.data.domain.Pixel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixelMapper @Inject constructor() {

    fun fromEntity(entityPixel: EntityPixel) : Pixel {
        return Pixel(
            date = entityPixel.date,
            mood = entityPixel.mood,
            note = entityPixel.note
        )
    }

    fun fromEntities(entities: List<EntityPixel>) : List<Pixel> {
        return entities.map { fromEntity(it) }
    }

    fun fromDomain(pixel: Pixel) : EntityPixel {
        return EntityPixel(
            date = pixel.date,
            mood = pixel.mood,
            note = pixel.note
        )
    }
}