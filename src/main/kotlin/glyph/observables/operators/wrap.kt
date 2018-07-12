package glyph.observables.operators

import glyph.Glyph
import glyph.Observer
import glyph.observables.GlyphObservable
import glyph.observables.Observable
import glyph.observables.RemoveObserver

fun <T> Observable<T>.wrap(wrap: (Observer<T>) -> Observer<T>): Observable<T> =
        WrapObservable(this, wrap)

private class WrapObservable<T>(
        private val observable: Observable<T>,
        private val wrap: (Observer<T>) -> Observer<T>
) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            wrap(observer).let(observable::addObserver)
}

fun <T> GlyphObservable<T>.wrap(wrap: (Glyph<T>, Observer<T>) -> Observer<T>): GlyphObservable<T> =
        WrapGlyphObservable(this, wrap)

private class WrapGlyphObservable<T>(
        private val observable: GlyphObservable<T>,
        private val wrap: (Glyph<T>, Observer<T>) -> Observer<T>
) : GlyphObservable<T> by observable {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            wrap(glyph, observer).let(observable::addObserver)

}