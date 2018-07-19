package io.lamart.glyph

interface OptionalGlyphSource<T> {

    fun get(): T?

    fun set(state: T)

    fun set(transform: T.(T) -> T) {
        get()?.let { transform(it, it) }?.let { this@OptionalGlyphSource.set(it) }
    }

    fun setIf(predicate: T.(T) -> Boolean, transform: T.(T) -> T) {
        get()
                ?.takeIf { predicate(it, it) }
                ?.let { transform(it, it) }
                ?.let(::set)
    }

}