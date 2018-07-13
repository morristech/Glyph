package io.lamart.glyph.observables

import io.lamart.glyph.Observer

interface Observable<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

}

