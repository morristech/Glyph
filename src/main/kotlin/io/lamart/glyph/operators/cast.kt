package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

class CastGlyph<T, R : T>(private val glyph: Glyph<T>) : Glyph<R> {

    override fun observe(): Observable<R> = glyph.observe().cast()

    @Suppress("UNCHECKED_CAST")
    override fun get(): R = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

}

class CastOptionalGlyph<T, R : T>(private val glyph: OptionalGlyph<T>) : OptionalGlyph<R> {

    override fun observe(): Observable<R?> = glyph.observe().cast()

    @Suppress("UNCHECKED_CAST")
    override fun get(): R? = glyph.get() as R?

    override fun set(state: R) = glyph.set(state)

}