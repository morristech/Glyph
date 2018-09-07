package io.lamart.glyph

import io.lamart.glyph.observable.Observable

interface OptionalGlyphSource<T> {

    fun get(): T?

    fun set(state: T)

    fun reduce(block: T.(T) -> T) {
        get()?.let { block(it, it) }?.let(::set)
    }

    fun observe(): Observable<T?>

}