package io.lamart.glyph.car

import io.lamart.glyph.implementation.SimpleGlyph
import io.lamart.glyph.emitter.ListEmitter
import org.junit.Test


class PersonTests {

    @Test
    fun test() {
        val observable = ListEmitter<Person>()
        val source = SimpleGlyph(Person(), observable)
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