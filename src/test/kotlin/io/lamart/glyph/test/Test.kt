package io.lamart.glyph.test

import glyph.SourceGlyph
import glyph.composers.compose


private fun test() {
    val source = SourceGlyph(Person())
    val cars = source
            .compose({ cars }, { copy(cars = it) })
            .let(::CarsInteractor)
}