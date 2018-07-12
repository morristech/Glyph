package io.lamart.glyph.observables.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.observables.Observable
import io.lamart.glyph.observables.RemoveObserver

/**
 * Creates an Observable that returns is applicable for a substate of the given Observable
 */

fun <T, R> Observable<T>.map(block: (T) -> R): Observable<R> = MapObservable(this, block)

private class MapObservable<T, R>(
        private val observable: Observable<T>,
        private val map: (T) -> R
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver { state -> observer(map(state)) }

}