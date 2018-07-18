package io.lamart.glyph.composition

import io.lamart.glyph.composition.house.HouseFeatures
import io.lamart.glyph.composition.house.HouseInteractor
import io.lamart.glyph.composition.user.User
import io.lamart.glyph.composition.user.UserFeatures
import io.lamart.glyph.composition.user.UserInteractor
import io.lamart.glyph.implementations.SimpleGlyph
import io.lamart.glyph.observables.ListEmitter
import io.lamart.glyph.Observable
import io.lamart.glyph.observables.operators.prepend
import io.lamart.glyph.operators.compose
import io.lamart.glyph.operators.filter

class StateInteractor(
        private val user: UserFeatures,
        private val house: HouseFeatures,
        override val observable: Observable<State>
) : StateFeatures,
        UserFeatures by user,
        HouseFeatures by house {

    companion object {

        fun newInstance(): StateInteractor {
            val delegate = ListEmitter<State>()
            val glyph = SimpleGlyph(State(), delegate)
            val observable = delegate.prepend(glyph)

            val userFeatures: UserFeatures = glyph
                    .compose({ user }, { copy(user = it) })
                    .let { UserInteractor(NetworkModule.Instance, it) }
            val houseFeatures: HouseFeatures = glyph
                    .filter { user is User.LoggedIn }
                    .compose({ user as User.LoggedIn }, { copy(user = it) })
                    .compose({ house }, { copy(house = it) })
                    .let(::HouseInteractor)

            return StateInteractor(
                    userFeatures,
                    houseFeatures,
                    observable
            )
        }

    }

}

