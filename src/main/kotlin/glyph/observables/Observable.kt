package glyph.observables

import glyph.Glyph
import glyph.Observer

typealias RemoveObserver = () -> Unit

interface Observable<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

}

interface GlyphObservable<T> : Observable<T> {

    val glyph: Glyph<T>

}