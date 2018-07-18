package io.lamart.glyph.observable

import io.lamart.glyph.Glyph
import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.operators.*

interface Observable<T> : ObservableSource<T> {

    fun delegate(delegate: (T, Observer<T>) -> Unit): Observable<T> =
            DelegateObservable(this, delegate)

    fun distinct(): Observable<T> =
            DistinctObservable(this)

    fun filter(predicate: (T) -> Boolean): Observable<T> =
            FilterObservable(this, predicate)

    fun <R> map(block: (T) -> R): Observable<R> =
            MapObservable(this, block)

    fun prepend(glyph: Glyph<T>): Observable<T> =
            PrependObservable(glyph, this)

    fun test(): TestObserver<T> = TestObserver<T>().also { addObserver(it) }

    fun test(block: TestObserver<T>.() -> Unit): RemoveObserver =
            TestObserver<T>().apply(block).let(::addObserver)

    fun wrap(wrap: (Observer<T>) -> Observer<T>): Observable<T> =
            WrapObservable(this, wrap)

}