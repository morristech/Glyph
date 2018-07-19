package io.lamart.glyph.operators.collections

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph

fun <K, V> Glyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapGlyph(this, key)

fun <K, V> OptionalGlyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapOptionalGlyph(this, key)

class ComposeMapGlyph<K, V>(
        private val glyph: Glyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> by ComposeMapOptionalGlyph(glyph.asOptional(), key)

open class ComposeMapOptionalGlyph<K, V>(
        private val glyph: OptionalGlyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> {

    override fun get(): V? = glyph.get()?.get(key)

    override fun set(state: V) {
        glyph.get()
                ?.toMutableMap()
                ?.apply { put(key, state) }
                ?.let(glyph::set)
    }

}