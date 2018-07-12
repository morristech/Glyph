package io.lamart.glyph.implementations

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer
import io.lamart.glyph.ObserverCreator
import io.lamart.glyph.ThreadSafe

class SynchronizedGlyph<T>(
        private var state: T,
        observerCreator: ObserverCreator<T>? = null,
        private val lock: Any = Any()
) : Glyph<T> {

    private val observer: Observer<T>? = observerCreator?.invoke(this)

    override fun getState(): T = synchronized(lock) { state }

    override fun setState(state: T) {
        synchronized(lock) { this.state = state }
        observer?.invoke(state)
    }

    @ThreadSafe
    fun ifState(predicate: T.(T) -> Boolean, block: Glyph<T>.() -> Unit) =
            synchronized(lock) {
                getState().takeIf { predicate(it, it) }?.let { block(this) }
            }

}