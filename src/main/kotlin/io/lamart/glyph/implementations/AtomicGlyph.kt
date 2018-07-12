package io.lamart.glyph.implementations

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer
import io.lamart.glyph.ObserverCreator
import java.util.concurrent.atomic.AtomicReference

class AtomicGlyph<T>(
        state: T,
        observerCreator: ObserverCreator<T>? = null
) : Glyph<T> {

    private val state = AtomicReference<T>(state)
    private val observer: Observer<T>? = observerCreator?.invoke(this)

    override fun getState(): T = state.get()

    override fun setState(state: T) {
        this.state.set(state)
        observer?.invoke(state)
    }

}