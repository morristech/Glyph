package io.lamart.glyph.observable.operators

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

/**
 * Creates an Observable that returns is applicable for a substate of the given Observable
 */

internal class MapObservable<T, R>(
        private val observable: Observable<T>,
        private val map: (T) -> R
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver { state -> observer(map(state)) }

}

