package io.lamart.glyph.test

import glyph.SimpleGlyph
import glyph.composers.compose
import glyph.intercept
import glyph.observables.*


private fun test() {
    val adapter = ListObserverAdapter<Person>()
    val source = SimpleGlyph(Person(), adapter)
    val cars = source
            .intercept({ getState() }, { it -> setState(it) })
            .compose({ cars }, { copy(cars = it) })
            .let(::CarsInteractor)

    adapter
            .prepend()
            .map { it.cars.find { it.name == "Ferrari" } }
            .filterNull()
            .distinct()
            .addObserver { it.name  }
}


