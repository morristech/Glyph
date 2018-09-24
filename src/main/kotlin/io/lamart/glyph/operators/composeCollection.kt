package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable

fun <T> Glyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeCollectionGlyph(this, predicate)

fun <T> OptionalGlyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeCollectionOptionalGlyph(this, predicate)

class ComposeCollectionGlyph<T>(
        private val glyph: Glyph<Collection<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeCollectionOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeCollectionOptionalGlyph<T>(
        private val glyph: OptionalGlyph<Collection<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> = glyph.observable.map { it?.find(predicate) }

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) = transform { state }

    override fun transform(transformer: Transformer<T>) =
            glyph.transform {
                find(predicate)?.let { state ->
                    toMutableSet().apply {
                        remove(state)
                        add(transformer(state, state))
                    }
                } ?: this
            }

}

