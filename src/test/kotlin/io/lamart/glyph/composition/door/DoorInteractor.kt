package io.lamart.glyph.composition.door

import io.lamart.glyph.OptionalGlyph

class DoorInteractor(glyph: OptionalGlyph<Door>) : DoorFeatures, OptionalGlyph<Door> by glyph {

    override fun open() = set { door -> Door.Open(door.location) }

    override fun close() = set { location -> Door.Closed(location.location) }

}