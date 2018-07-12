package glyph.implementations

import glyph.Glyph
import glyph.Observer
import glyph.ObserverCreator
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