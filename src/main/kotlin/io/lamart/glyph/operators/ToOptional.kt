package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

internal class ToOptional<T>(private val glyph: Glyph<T>) : OptionalGlyph<T> {

    override fun observe(): Observable<T?> = glyph.observe().cast()

    override fun get(): T? = glyph.get()

    override fun set(state: T) = glyph.set(state)

}