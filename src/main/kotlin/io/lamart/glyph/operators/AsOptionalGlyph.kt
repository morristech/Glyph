package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class AsOptionalGlyph<T>(private val glyph: Glyph<T>) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()

    override fun setState(state: T) = glyph.setState(state)

}