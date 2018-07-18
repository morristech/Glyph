package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph

open class SimpleGlyph<T>(
        private var state: T,
        private val observer: Observer<T> = {}
) : Glyph<T> {

    override fun getState(): T = state

    override fun setState(state: T) {
        this.state = state
        observer(state)
    }

}
