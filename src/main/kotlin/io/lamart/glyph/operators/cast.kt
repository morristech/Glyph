package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.observable.Observable

@Suppress("UNCHECKED_CAST")
class CastGlyph<T, R : T>(private val glyph: Glyph<T>) : Glyph<R> {

    override val observable: Observable<R> = glyph.observable.cast()

    override fun get(): R = glyph.get() as R

    override fun set(state: R) = glyph.set(state)

    override fun transform(transformer: R.(R) -> R) {
        glyph.transform {
            transformer(it as R, it)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class CastOptionalGlyph<T, R : T>(private val glyph: OptionalGlyph<T>) : OptionalGlyph<R> {

    override val observable: Observable<R?> = glyph.observable.cast()

    override fun get(): R? = glyph.get() as R?

    override fun set(state: R) = glyph.set(state)

    override fun transform(transformer: R.(R) -> R) {
        glyph.transform {
            transformer(it as R, it)
        }
    }

}