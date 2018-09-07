package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

internal class FilterGlyph<T>(
        private val glyph: Glyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun observe(): Observable<T?> =
            glyph.observe().filter(predicate).cast()

    override fun get(): T? = glyph.get().takeIf(predicate)

    override fun set(state: T) = glyph.reduce { state }

}

internal class FilterOptionalGlyph<T>(
        private val glyph: OptionalGlyph<T>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by glyph {

    override fun get(): T? = glyph.get()?.takeIf(predicate)

    override fun set(state: T) = glyph.set(state)

}