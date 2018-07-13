package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <T> Glyph<T>.filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
        FilterOptionalGlyph(this.toOptional(), predicate)

private class FilterGlyph<T>(
        private val glyph: Glyph<T>,
        private val predicate: T.(T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState().takeIf { predicate(it, it) }

    override fun setState(state: T) = glyph.setState { state }

}

fun <T> OptionalGlyph<T>.filter(predicate: T.(T) -> Boolean): OptionalGlyph<T> =
        FilterOptionalGlyph(this, predicate)

private class FilterOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val predicate: T.(T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()?.takeIf { predicate(it, it) }

    override fun setState(state: T) = glyph.setState { state }

}