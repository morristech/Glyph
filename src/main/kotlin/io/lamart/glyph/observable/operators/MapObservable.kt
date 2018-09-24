package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable

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

