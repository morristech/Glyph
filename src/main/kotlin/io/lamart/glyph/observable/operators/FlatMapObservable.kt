package io.lamart.glyph.observable.operators

import io.lamart.glyph.observable.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

internal class FlatMapObservable<T, R>(
        private val observable: Observable<T>,
        private val factory: (T) -> Iterable<R>
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver{ state -> factory(state).forEach(observer) }

}