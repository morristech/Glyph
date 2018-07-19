package io.lamart.glyph.composition.house

import io.lamart.glyph.composition.door.Location
import io.lamart.glyph.composition.door.Door

data class House(val doors: List<Door> = listOf(
        Door.Closed(Location.FRONT),
        Door.Closed(Location.FRONT)
))