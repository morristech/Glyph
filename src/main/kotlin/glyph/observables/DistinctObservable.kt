package glyph.observables

import glyph.Observer


fun <T> Observable<T>.distinct(): Observable<T> = DistinctObservable(this)

private class DistinctObservable<T>(private val observable: Observable<T>) : Observable<T> {

    override fun addObserver(observer: Observer<T>): RemoveObserver =
            observable.addObserver(DistinctObserver(observer))

    private inner class DistinctObserver(private val observer: Observer<T>) : Observer<T> {

        private var isSet = false
        private var state: T? = null

        override fun invoke(next: T) {
            if (!isSet) {
                state = next
                isSet = true
                observer(next)
            } else if (state != next) {
                state = next
                observer(next)
            }
        }

    }
}