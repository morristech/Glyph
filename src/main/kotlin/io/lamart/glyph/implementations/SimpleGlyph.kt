package io.lamart.glyph.implementations

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer

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
