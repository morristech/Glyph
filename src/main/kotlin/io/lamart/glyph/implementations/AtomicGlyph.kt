package io.lamart.glyph.implementations

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer
import java.util.concurrent.atomic.AtomicReference

open class AtomicGlyph<T>(
        state: T,
        private val observer: Observer<T> = {}
) : Glyph<T> {

    private val state = AtomicReference<T>(state)

    override fun getState(): T = state.get()

    override fun setState(state: T) {
        this.state.set(state)
        observer(state)
    }

}