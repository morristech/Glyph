package io.lamart.glyph.composition.house

import io.lamart.glyph.composition.door.Location

interface HouseFeatures {

    fun openDoor(location: Location)

    fun closeDoor(location: Location)

}