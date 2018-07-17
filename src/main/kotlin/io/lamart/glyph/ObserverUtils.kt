package io.lamart.glyph

typealias Observer<T> = (T) -> Unit

typealias RemoveObserver = () -> Unit

interface Observable<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

}

interface Emitter<T>: Observer<T>, Observable<T>
