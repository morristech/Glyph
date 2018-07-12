package io.lamart.glyph.implementations

import io.lamart.glyph.Glyph
import io.lamart.glyph.Observer

/**
 * This function will only be called once
 */

typealias ObserverCreator<T> = (Glyph<T>) -> Observer<T>