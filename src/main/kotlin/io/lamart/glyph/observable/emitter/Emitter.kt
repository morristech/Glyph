package io.lamart.glyph.observable.emitter

import io.lamart.glyph.Observer
import io.lamart.glyph.observable.Observable

interface Emitter<T> : Observer<T>, Observable<T>