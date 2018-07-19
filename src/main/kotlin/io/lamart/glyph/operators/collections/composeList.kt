package io.lamart.glyph.operators.collections

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph


fun <T> Glyph<List<T>>.composeList(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeListGlyph(this@composeList, predicate)

fun <T> OptionalGlyph<List<T>>.composeList(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeListOptionalGlyph(this@composeList, predicate)

class ComposeListGlyph<T>(
        private val glyph: Glyph<List<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeListOptionalGlyph(glyph.asOptional(), predicate)

open class ComposeListOptionalGlyph<T>(
        private val glyph: OptionalGlyph<List<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) {
        glyph.get()
                ?.toMutableList()
                ?.also { replace(it, state) }
                ?.let(glyph::set)
    }

    private fun replace(list: MutableList<T>, state: T) =
            with(list) {
                indexOfFirst(predicate)
                        .takeIf { it != -1 }
                        ?.also { index -> set(index, state) }
            }

}

