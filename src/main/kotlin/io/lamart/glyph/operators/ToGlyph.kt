package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class ToGlyph<out T, R : T>(
        private val glyph: Glyph<T>,
        private val block: T.() -> R?
) : OptionalGlyph<R> {

    override fun get(): R? = glyph.get().let(block)

    override fun set(state: R) = glyph.set(state)

}

internal class ToOptionalGlyph<out T, R : T>(
        private val glyph: OptionalGlyph<T>,
        private val block: T.() -> R?
) : OptionalGlyph<R> {

    override fun get(): R? = glyph.get()?.let(block)

    override fun set(state: R) = glyph.set(state)

}