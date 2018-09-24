package io.lamart.glyph.composition.house

import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.composition.door.DoorInteractor
import io.lamart.glyph.composition.door.Location
import io.lamart.glyph.operators.composeList

class HouseInteractor(glyph: OptionalGlyph<House>) : HouseFeatures, OptionalGlyph<House> by glyph {

    override fun openDoor(location: Location) = getDoor(location).open()

    override fun closeDoor(location: Location) = getDoor(location).close()

    private fun getDoor(location: Location): DoorInteractor =
            compose({ doors }, { copy(doors = it) })
                    .composeList({ it.location == location })
                    .let(::DoorInteractor)

}