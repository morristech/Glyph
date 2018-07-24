package io.lamart.glyph

interface OptionalGlyphSource<T> {

    fun get(): T?

    fun set(state: T)

    fun set(transform: T.(T) -> T) {
        get()?.let { transform(it, it) }?.let { set(it) }
    }

}