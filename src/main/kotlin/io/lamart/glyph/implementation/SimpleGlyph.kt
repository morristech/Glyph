package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.emitter.Emitter
import io.lamart.glyph.observable.emitter.ListEmitter

open class SimpleGlyph<T>(
        private var state: T,
        private val emitter: Emitter<T> = ListEmitter()
) : Glyph<T> {

    override fun observe(): Observable<T> = emitter

    override fun get(): T = state

    override fun set(state: T) {
        this.state = state
        emitter(state)
    }

}
