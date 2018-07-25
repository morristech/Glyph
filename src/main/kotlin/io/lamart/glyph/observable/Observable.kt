package io.lamart.glyph.observable

import io.lamart.glyph.Glyph
import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.operators.*

interface Observable<T> : ObservableSource<T> {

    fun <R> cast(): Observable<R> = CastObservable(this)

    fun distinct(): Observable<T> =
            DistinctObservable(this)

    fun filter(predicate: (T) -> Boolean): Observable<T> =
            FilterObservable(this, predicate)

    fun <R> flatMap(delegate: (T, Observer<R>) -> Unit): Observable<R> =
            FlatMapObservable(this, delegate)

    fun <R> flatMap(delegate: (T) -> Iterable<R>): Observable<R> =
            FlatMapIterableObservable(this, delegate)

    fun <R> map(block: (T) -> R): Observable<R> =
            MapObservable(this, block)

    fun prepend(glyph: Glyph<T>): Observable<T> =
            PrependObservable(glyph, this)

    fun test(): TestObserver<T> = TestObserver<T>().also { addObserver(it) }

    fun test(block: TestObserver<T>.() -> Unit): RemoveObserver =
            TestObserver<T>().apply(block).let(::addObserver)

    fun <R> lift(wrap: (Observer<R>) -> Observer<T>): Observable<R> =
            LiftObservable(this, wrap)

}