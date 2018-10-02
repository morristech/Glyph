/*
 * Copyright (c) 2018 Danny Lamarti.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.lamart.glyph.composition

import io.lamart.glyph.SimpleGlyph
import io.lamart.glyph.composition.house.HouseFeatures
import io.lamart.glyph.composition.house.HouseInteractor
import io.lamart.glyph.composition.user.User
import io.lamart.glyph.composition.user.UserFeatures
import io.lamart.glyph.composition.user.UserInteractor
import io.lamart.glyph.emitter.ListEmitter
import io.lamart.glyph.observable.Observable

@Suppress("ReplaceSingleLineLet")
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
            val observable = delegate.prepend(glyph::get)

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

