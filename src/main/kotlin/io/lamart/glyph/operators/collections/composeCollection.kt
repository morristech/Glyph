package io.lamart.glyph.operators.collections

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

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

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) {
        glyph.set {
            toMutableSet().apply {
                find(predicate)?.also { remove(it); add(state) }
            }
        }
    }

}

