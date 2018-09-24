package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable

internal class FlatMapObservable<T, R>(
        private val observable: Observable<T>,
        private val factory: (T) -> Iterable<R>
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver{ state -> factory(state).forEach(observer) }

}