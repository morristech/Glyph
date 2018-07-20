package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class CheckGlyph<out T, R : T>(
        private val glyph: Glyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<R> {

    @Suppress("UNCHECKED_CAST")
    override fun get(): R? = glyph.get().takeIf(predicate)?.let { it as R }

    override fun set(state: R) = glyph.set(state)

}

internal class CheckOptionalGlyph<out T, R : T>(
        private val glyph: OptionalGlyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<R> {

    @Suppress("UNCHECKED_CAST")
    override fun get(): R? = glyph.get()?.takeIf(predicate)?.let { it as R }

    override fun set(state: R) = glyph.set(state)

}