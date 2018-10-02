package io.lamart.glyph.operators

import io.lamart.glyph.Glyph
import io.lamart.glyph.OptionalGlyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable

fun <K, V> Glyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapGlyph(this, key)

fun <K, V> OptionalGlyph<Map<K, V>>.composeMap(key: K): OptionalGlyph<V> =
        ComposeMapOptionalGlyph(this, key)

class ComposeMapGlyph<K, V>(
        private val glyph: Glyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> by ComposeMapOptionalGlyph(glyph.toOptional(), key)

open class ComposeMapOptionalGlyph<K, V>(
        private val glyph: OptionalGlyph<Map<K, V>>,
        private val key: K
) : OptionalGlyph<V> {

    override val observable: Observable<V?> = glyph.observable.map { map -> map?.get(key) }

    override fun get(): V? = glyph.get()?.get(key)

    override fun set(state: V) =
            glyph.set { map ->
                when {
                    map.containsKey(key) -> map.toMutableMap().apply { put(key, state) }
                    else -> map
                }
            }

    override fun set(transform: Transformer<V>) =
            glyph.set { map ->
                map[key]?.let { state ->
                    map.toMutableMap().apply {
                        put(key, transform(state))
                    }
                } ?: map
            }


}