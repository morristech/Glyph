package io.lamart.glyph.composition.door

import io.lamart.glyph.OptionalGlyph

class DoorInteractor(glyph: OptionalGlyph<Door>) : DoorFeatures, OptionalGlyph<Door> by glyph {

    override fun open() = reduce { Door.Open(location) }

    override fun close() = reduce { Door.Closed(location) }

}