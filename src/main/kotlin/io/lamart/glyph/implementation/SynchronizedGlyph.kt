package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph

open class SynchronizedGlyph<T>(
        private var state: T,
        private val observer: Observer<T> = {}
) : Glyph<T> {

    private val lock: Any = Any()

    override fun getState(): T = synchronized(lock) { state }

    override fun setState(state: T) {
        synchronized(lock) { this.state = state }
        observer(state)
    }

    override fun setState(transform: T.(T) -> T) = synchronized(lock) { super.setState(transform) }

    override fun setStateIf(predicate: T.(T) -> Boolean, transform: T.(T) -> T) =
            synchronized(lock) { super.setStateIf(predicate, transform) }

}