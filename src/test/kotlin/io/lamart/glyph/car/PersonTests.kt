package io.lamart.glyph.car

import io.lamart.glyph.implementations.BasicGlyph
import io.lamart.glyph.operators.compose
import io.lamart.glyph.observables.*
import io.lamart.glyph.observables.operators.*
import org.junit.Test


class PersonTests {

    @Test
    fun test() {
        val observable = ListEmitter<Person>()
        val source = BasicGlyph(Person(), observable)
        val cars = source
                .compose({ cars }, { copy(cars = it) })
                .let(::CarsInteractor)

        observable
                .prepend(source)
                .map { it.cars.find { it.name == "Ferrari" }?.name }
                .distinct()
                .addObserver(::println)

        cars.stopHonking("Ferrari")
    }


}