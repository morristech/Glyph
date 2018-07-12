package io.lamart.glyph

import io.lamart.glyph.implementations.BasicGlyph
import io.lamart.glyph.operators.compose
import io.lamart.glyph.observables.*
import io.lamart.glyph.observables.operators.*


class Test {

    private fun test() {
        val observable = ObserverCreatorAdapter<Person>(::ListProcessor)
        val source = BasicGlyph(Person(), observable)
        val cars = source
                .compose({ cars }, { copy(cars = it) })
                .let(::CarsInteractor)

        observable
                .prepend()
                .map { it.cars.find { it.name == "Ferrari" } }
                .filterNull()
                .distinct()
                .addObserver { it.name }

        cars.startHonking("Ferrari")
        cars.stopHonking("Ferrari")

    }


}