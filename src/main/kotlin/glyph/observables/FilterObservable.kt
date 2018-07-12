package glyph.observables

import glyph.Observer

fun <T> Observable<T>.filter(predicate: (T) -> Boolean): Observable<T> = FilterObservable(this, predicate)

fun <T> Observable<T?>.filterNull(): Observable<T> = filter({ it != null }).map { it!! }

class FilterObservable<T>(private val observable: Observable<T>, private val predicate: (T) -> Boolean) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(FilterObserver(observer))

    private inner class FilterObserver(private val observer: Observer<T>) : Observer<T> {

        override fun invoke(state: T) {
            state.takeIf(predicate)?.let(observer)
        }

    }

}