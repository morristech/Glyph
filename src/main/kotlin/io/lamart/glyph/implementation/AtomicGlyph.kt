package io.lamart.glyph.implementation

import io.lamart.glyph.Glyph
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.emitter.Emitter
import io.lamart.glyph.observable.emitter.SynchronizedListEmitter
import java.util.concurrent.atomic.AtomicReference

open class AtomicGlyph<T>(
        state: T,
        private val emitter: Emitter<T> = SynchronizedListEmitter()
) : Glyph<T> {

    private val reference = AtomicReference<T>(state)

    override fun get(): T = reference.get()

    override fun set(state: T) {
        reference.set(state)
        emitter(state)
    }

    override fun observe(): Observable<T> = emitter

}