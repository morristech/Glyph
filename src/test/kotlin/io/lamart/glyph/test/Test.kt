package io.lamart.glyph.test

import glyph.SimpleGlyph
import glyph.composers.compose
import glyph.observables.*
import glyph.observables.operators.*


private fun test() {
    val observable = ListObserverAdapter<Person>()
    val source = SimpleGlyph(Person(), observable)
    val cars = source
            .compose({ cars }, { copy(cars = it) })
            .let(::CarsInteractor)

    observable
            .prepend()
            .map { it.cars.find { it.name == "Ferrari" } }
            .filterNull()
            .distinct()
            .addObserver { it.name }
}


