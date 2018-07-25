package io.lamart.glyph.observable.operators

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable
import io.lamart.glyph.observable.RemoveObserver

internal class CastObservable<T, R>(private val observable: Observable<T>) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver(CastObserver(observer))

    @Suppress("UNCHECKED_CAST")
    private inner class CastObserver(observer: Observer<R>) : Observer<T> by { observer(it as R) }

}