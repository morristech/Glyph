package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph

open class SimpleGlyph<T>(
        private var state: T,
        private val observer: Observer<T> = {}
) : Glyph<T> {

    override fun get(): T = state

    override fun set(state: T) {
        this.state = state
        observer(state)
    }

}
