package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.emitter.Emitter
import io.lamart.glyph.observable.emitter.SynchronizedListEmitter


class SynchronizedGlyph<T>(
        private var state: T,
        private val emitter: Emitter<T> = SynchronizedListEmitter()
) : Glyph<T> {

    val lock: Any = Any()

    override fun get(): T = synchronized(lock) { state }

    override fun set(state: T) {
        synchronized(lock) {
            this.state = state
        }
        emitter(state)
    }

    override fun reduce(block: T.(T) -> T) =
            synchronized(lock) { super.reduce(block) }

    override fun observe(): Observable<T> = emitter

    fun synchronized(block: Glyph<T>.() -> Unit) = synchronized(lock) { block() }

}