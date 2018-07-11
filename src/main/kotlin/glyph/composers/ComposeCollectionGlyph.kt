package glyph.composers

import glyph.Glyph
import glyph.OptionalGlyph
import glyph.toOptional


fun <T> Glyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean, init: OptionalGlyph<T>.() -> Unit) =
        composeCollection(predicate).run(init)

fun <T> Glyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeCollectionGlyph(this@composeCollection, predicate)

fun <T> OptionalGlyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean, init: OptionalGlyph<T>.() -> Unit) =
        composeCollection(predicate).run(init)

fun <T> OptionalGlyph<Collection<T>>.composeCollection(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeCollectionOptionalGlyph(this@composeCollection, predicate)

class ComposeCollectionGlyph<T>(
        private val glyph: Glyph<Collection<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeCollectionOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeCollectionOptionalGlyph<T>(
        private val glyph: OptionalGlyph<Collection<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun getState(): T? = glyph.getState()?.find(predicate)

    override fun setState(state: T) {
        glyph.getState()
                ?.toMutableSet()
                ?.also { replace(it, state) }
                ?.let(glyph::setState)
    }

    private fun replace(list: MutableCollection<T>, state: T) =
            with(list) {
                find(predicate)?.also { remove(it); add(state) }
            }

}

