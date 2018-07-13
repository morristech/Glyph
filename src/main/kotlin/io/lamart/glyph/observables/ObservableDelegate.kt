package io.lamart.glyph.observables

import io.lamart.glyph.Observer

interface ObservableDelegate<T>: Observer<T>, Observable<T>