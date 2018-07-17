package io.lamart.glyph.implementations

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer

open class SynchronizedGlyph<T>(
        private var state: T,
        private val observer: Observer<T> = {},
        private val lock: Any = Any()
) : Glyph<T> {

    override fun getState(): T = synchronized(lock) { state }

    override fun setState(state: T) {
        synchronized(lock) { this.state = state }
        observer(state)
    }

    override fun setState(transform: T.(T) -> T) = synchronized(lock) { super.setState(transform) }

    override fun setStateIf(predicate: T.(T) -> Boolean, transform: T.(T) -> T) =
            synchronized(lock) { super.setStateIf(predicate, transform) }

}