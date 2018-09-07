package io.lamart.glyph.observable

import io.lamart.glyph.observable.operators.*

interface Observable<T> : ObservableSource<T> {

    fun <R> cast(): Observable<R> = CastObservable(this)

    fun <R> delegate(delegate: (T, Observer<R>) -> Unit): Observable<R> =
            DelegateObservable(this, delegate)

    fun distinct(): Observable<T> =
            DistinctObservable(this)

    fun filter(predicate: (T) -> Boolean): Observable<T> =
            FilterObservable(this, predicate)

    fun <R> flatMap(delegate: (T) -> Iterable<R>): Observable<R> =
            FlatMapObservable(this, delegate)

    fun <R> lift(wrap: (Observer<R>) -> Observer<T>): Observable<R> =
            LiftObservable(this, wrap)

    fun <R> map(block: (T) -> R): Observable<R> =
            MapObservable(this, block)

    fun prepend(getState: () -> T): Observable<T> =
            PrependObservable( this, getState)

    fun take(count: Long): Observable<T> = TakeObservable(this, count)

    fun test(): TestObserver<T> = TestObserver<T>().also { addObserver(it) }

    fun test(block: TestObserver<T>.() -> Unit): RemoveObserver =
            TestObserver<T>().apply(block).let(::addObserver)

}