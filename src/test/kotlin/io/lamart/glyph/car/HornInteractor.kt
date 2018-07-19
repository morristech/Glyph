package io.lamart.glyph.car

import io.lamart.glyph.OptionalGlyph

class HornInteractor(glyph: OptionalGlyph<Horn>) : OptionalGlyph<Horn> by glyph {

    fun startHonking() = this@HornInteractor.set(Horn(isHonking = true))

    fun stopHonking() = this@HornInteractor.set(Horn(isHonking = false))

}