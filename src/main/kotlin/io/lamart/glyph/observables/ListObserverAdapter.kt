package io.lamart.glyph.observables

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer

class ListObserverAdapter<T> : ObserverAdapter<T> {

    private lateinit var observable: ListObservable<T>
    override val glyph: Glyph<T>
        get() = observable.glyph

    override fun invoke(glyph: Glyph<T>): ListObservable<T> = ListObservable(glyph).also { observable = it }

    override fun addObserver(observer: Observer<T>): RemoveObserver = observable.addObserver(observer)

}