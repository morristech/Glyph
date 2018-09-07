package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class ListenGlyph<T>(
        private val glyph: Glyph<T>,
        private val getState: (T) -> Unit,
        private val setState: (T) -> Unit
) : Glyph<T> by glyph {

    override fun get(): T = glyph.get().also(getState)

    override fun set(state: T) {
        glyph.set(state)
        setState.invoke(state)
    }

}

internal class ListenOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val getState: (T?) -> Unit,
        private val setState: (T) -> Unit
) : OptionalGlyph<T> by glyph {

    override fun get(): T? = glyph.get().also(getState)

    override fun set(state: T) {
        glyph.set(state)
        setState.invoke(state)
    }

}