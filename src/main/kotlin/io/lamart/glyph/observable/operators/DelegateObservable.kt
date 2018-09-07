package io.lamart.glyph.observable.operators

import io.lamart.glyph.observable.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

internal class DelegateObservable<T, R>(
        private val observable: Observable<T>,
        private val delegate: (T, Observer<R>) -> Unit
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver{ state -> delegate(state, observer) }

}