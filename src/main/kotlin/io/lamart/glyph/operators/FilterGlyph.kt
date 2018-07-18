package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

internal class FilterGlyph<T>(
        private val glyph: Glyph<T>,
        private val predicate: T.(T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState().takeIf { predicate(it, it) }

    override fun setState(state: T) = glyph.setState { state }

}

internal class FilterOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val predicate: T.(T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()?.takeIf { predicate(it, it) }

    override fun setState(state: T) = glyph.setState { state }

}