package io.lamart.glyph.observables

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer

typealias RemoveObserver = () -> Unit

interface Observable<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

}

interface GlyphObservable<T> : Observable<T> {

    val glyph: Glyph<T>

}

