package io.lamart.glyph.observable.emitter

import io.lamart.glyph.Observer
import io.lamart.glyph.observable.Observable

/**
 * Delegates the argument of Observer to all the observers registered by the Observable.
 */

interface Emitter<T> : Observer<T>, Observable<T>