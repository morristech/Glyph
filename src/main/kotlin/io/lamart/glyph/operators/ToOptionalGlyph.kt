package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class ToOptionalGlyph<T>(private val glyph: Glyph<T>) : OptionalGlyph<T> {

    override fun get(): T? = glyph.get()

    override fun set(state: T) = glyph.set(state)

}