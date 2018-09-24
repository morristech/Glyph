package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable

internal class ToOptional<T>(private val glyph: Glyph<T>) : OptionalGlyph<T> {

    override val observable: Observable<T?> = glyph.observable.map { it as T? }

    override fun get(): T? = glyph.get()

    override fun set(state: T) = glyph.set(state)

    override fun transform(transformer: Transformer<T>) =
            glyph.get().let { transformer(it, it) }.let(glyph::set)

}