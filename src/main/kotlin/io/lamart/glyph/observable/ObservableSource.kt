package io.lamart.glyph.observable

import io.lamart.glyph.implementation.Observer

interface ObservableSource<T> {

    fun addObserver(observer: Observer<T>): RemoveObserver

}