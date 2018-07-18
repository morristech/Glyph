package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class ListenGlyph<T>(
        private val glyph: Glyph<T>,
        private val getState: (T) -> Unit,
        private val setState: (T) -> Unit
) : Glyph<T> {

    override fun getState(): T = glyph.getState().also(getState)

    override fun setState(state: T) {
        glyph.setState(state)
        setState.invoke(state)
    }

}

internal class ListenOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val getState: (T?) -> Unit,
        private val setState: (T) -> Unit
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState().also(getState)

    override fun setState(state: T) {
        glyph.setState(state)
        setState.invoke(state)
    }

}