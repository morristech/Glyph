package io.lamart.glyph.car

import io.lamart.glyph.OptionalGlyph

class HornInteractor(glyph: OptionalGlyph<Horn>) : OptionalGlyph<Horn> by glyph {

    fun startHonking() = set(Horn(isHonking = true))

    fun stopHonking() = set(Horn(isHonking = false))

}