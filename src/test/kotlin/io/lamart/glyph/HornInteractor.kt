package io.lamart.glyph

import io.lamart.glyph.OptionalGlyph

class HornInteractor(glyph: OptionalGlyph<Horn>) : OptionalGlyph<Horn> by glyph {

    fun startHonking() = setState(Horn(isHonking = true))

    fun stopHonking() = setState(Horn(isHonking = false))

}