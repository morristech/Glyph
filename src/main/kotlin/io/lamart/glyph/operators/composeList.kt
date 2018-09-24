package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable


fun <T> Glyph<List<T>>.composeList(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeListGlyph(this, predicate)

fun <T> OptionalGlyph<List<T>>.composeList(predicate: (T) -> Boolean): OptionalGlyph<T> =
        ComposeListOptionalGlyph(this, predicate)

class ComposeListGlyph<T>(
        private val glyph: Glyph<List<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> by ComposeListOptionalGlyph(glyph.toOptional(), predicate)

open class ComposeListOptionalGlyph<T>(
        private val glyph: OptionalGlyph<List<T>>,
        private val predicate: (T) -> Boolean
) : OptionalGlyph<T> {

    override val observable: Observable<T?> = glyph.observable.map { it?.find(predicate) }

    override fun get(): T? = glyph.get()?.find(predicate)

    override fun set(state: T) =
            glyph.transform {
                indexOfFirst(predicate).takeIf { index -> index != -1 }?.let { index ->
                    toMutableList().apply {
                        set(index, state)
                    }
                } ?: this
            }

    override fun transform(transformer: Transformer<T>) =
            glyph.transform {
                indexOfFirst(predicate).takeIf { index -> index != -1 }?.let { index ->
                    get(index).let { state ->
                        toMutableList().apply {
                            set(index, transformer(state, state))
                        }
                    }
                } ?: this
            }

}

