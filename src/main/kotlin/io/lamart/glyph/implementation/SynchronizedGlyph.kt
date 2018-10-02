package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph
import io.lamart.glyph.Transformer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.emitter.Emitter
import io.lamart.glyph.observable.emitter.SynchronizedListEmitter


class SynchronizedGlyph<T>(
        private var state: T,
        private val emitter: Emitter<T> = SynchronizedListEmitter()
) : Glyph<T> {

    val lock: Any = Any()

    override val observable: Observable<T> = emitter

    override fun get(): T = synchronized(lock) { state }

    override fun set(state: T) {
        synchronized(lock) {
            this.state = state
        }
        emitter(state)
    }

    override fun set(transform: Transformer<T>) {
        synchronized(lock) {
            state = transform(state)
        }
        emitter(state)
    }

    fun synchronized(block: Glyph<T>.() -> Unit) = synchronized(lock) { block() }

}