package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

@Suppress("UNCHECKED_CAST")
class CastGlyph<T, R : T>(private val glyph: Glyph<T>) : Glyph<R> {

    override fun get(): R = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

}

@Suppress("UNCHECKED_CAST")
class CastOptionalGlyph<T, R : T>(private val glyph: OptionalGlyph<T>) : OptionalGlyph<R> {

    override fun get(): R? = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

}