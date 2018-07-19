package io.lamart.glyph.composition.door

import io.lamart.glyph.OptionalGlyph

class DoorInteractor(glyph: OptionalGlyph<Door>) : DoorFeatures, OptionalGlyph<Door> by glyph {

    override fun open() = set { copy(isOpen = true) }

    override fun close() = set { copy(isOpen = false) }

}