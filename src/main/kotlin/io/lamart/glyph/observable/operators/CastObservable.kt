package io.lamart.glyph.observable.operators

import io.lamart.glyph.Observer
import io.lamart.glyph.RemoveObserver
import io.lamart.glyph.observable.Observable

internal class CastObservable<T, R>(private val observable: Observable<T>) : Observable<R> {

    override fun addObserver(observer: Observer<R>): RemoveObserver =
            observable.addObserver(CastObserver(observer))

    @Suppress("UNCHECKED_CAST")
    private inner class CastObserver(observer: Observer<R>) : Observer<T> by { observer(it as R) }

}