package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

class CastGlyph<T, R : T>(private val glyph: Glyph<T>) : Glyph<R> {

    @Suppress("UNCHECKED_CAST")
    override fun get(): R = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

}

class CastOptionalGlyph<T, R : T>(private val glyph: OptionalGlyph<T>) : OptionalGlyph<R> {

    @Suppress("UNCHECKED_CAST")
    override fun get(): R? = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

}