package io.lamart.glyph.test

import io.lamart.glyph.implementations.SimpleGlyph
import io.lamart.glyph.operators.compose
import io.lamart.glyph.observables.*
import io.lamart.glyph.observables.operators.*


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


