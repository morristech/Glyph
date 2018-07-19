package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class InterceptGlyph<T>(
        private val glyph: Glyph<T>,
        private val getState: Glyph<T>.() -> T,
        private val setState: Glyph<T>.(T) -> Unit
) : Glyph<T> {

    override fun get(): T = getState(glyph)

    override fun set(state: T) = setState(glyph, state)

}

internal class InterceptOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val getState: OptionalGlyph<T>.() -> T?,
        private val setState: OptionalGlyph<T>.(T) -> Unit
) : OptionalGlyph<T> {

    override fun get(): T? = getState(glyph)

    override fun set(state: T) = setState(glyph, state)

}