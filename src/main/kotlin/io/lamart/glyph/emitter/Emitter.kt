package io.lamart.glyph.emitter

import io.lamart.glyph.implementation.Observer
import io.lamart.glyph.observable.Observable

interface Emitter<T> : Observer<T>, Observable<T>