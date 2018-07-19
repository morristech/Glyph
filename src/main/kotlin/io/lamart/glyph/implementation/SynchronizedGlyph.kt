package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph

open class SynchronizedGlyph<T>(
        private var state: T,
        private val observer: Observer<T> = {}
) : Glyph<T> {

    private val lock: Any = Any()

    override fun get(): T = synchronized(lock) { state }

    override fun set(state: T) {
        synchronized(lock) { this.state = state }
        observer(state)
    }

    override fun set(transform: T.(T) -> T) = synchronized(lock) { super.set(transform) }

    override fun setIf(predicate: T.(T) -> Boolean, transform: T.(T) -> T) =
            synchronized(lock) { super.setIf(predicate, transform) }

}