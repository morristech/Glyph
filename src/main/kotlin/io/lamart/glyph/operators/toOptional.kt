package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <T> Glyph<T>.toOptional(): OptionalGlyph<T> = ToOptionalGlyph(this)

private class ToOptionalGlyph<T>(private val glyph: Glyph<T>) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()

    override fun setState(state: T) = glyph.setState(state)

}