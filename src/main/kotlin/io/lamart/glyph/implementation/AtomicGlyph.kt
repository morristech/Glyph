package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph
import java.util.concurrent.atomic.AtomicReference

open class AtomicGlyph<T>(
        state: T,
        private val observer: Observer<T> = {}
) : Glyph<T> {

    private val reference = AtomicReference<T>(state)

    override fun getState(): T = reference.get()

    override fun setState(state: T) {
        reference.set(state)
        observer(state)
    }

}