package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable

internal class DelegateObservable<T, R>(
        private val observable: Observable<T>,
        private val delegate: (T, Observer<R>) -> Unit
) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver{ state -> delegate(state, observer) }

}