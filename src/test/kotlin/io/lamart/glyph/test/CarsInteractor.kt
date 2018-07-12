package io.lamart.glyph.test

import io.lamart.glyph.Glyph
import io.lamart.glyph.operators.compose
import io.lamart.glyph.operators.composeList

class CarsInteractor(private val glyph: Glyph<List<Car>>) {

    fun startHonking(carName: String) = getHornInteractor(carName).startHonking()

    fun stopHonking(carName: String) = getHornInteractor(carName).stopHonking()

    private fun getHornInteractor(carName: String): HornInteractor =
            glyph.composeList { car -> car.name == carName }
                    .compose({ steer }, { copy(steer = it) })
                    .compose({ horn }, { copy(horn = it) })
                    .let(::HornInteractor)

}