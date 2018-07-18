package io.lamart.glyph

interface GlyphSource<T> {

    fun getState(): T

    fun setState(state: T)

    fun setState(transform: T.(T) -> T) {
        getState().let { transform(it, it) }.let { setState(it) }
    }

    fun setStateIf(predicate: T.(T) -> Boolean, transform: T.(T) -> T) {
        getState()
                .takeIf { predicate(it, it) }
                ?.let { transform(it, it) }
                ?.let(::setState)
    }

}