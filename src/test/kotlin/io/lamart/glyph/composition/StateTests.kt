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

import io.lamart.glyph.composition.door.Door
import io.lamart.glyph.composition.door.Location
import io.lamart.glyph.composition.user.User
import org.junit.Test

class StateTests {

    @Test
    fun test() {
        val state: StateFeatures = StateInteractor.newInstance()

        state.observable
                .map { it.user }
                .test {
                    assertValueAt(0, { isNotLoggedIn })
                    assertValueAt(1, { isLoggingIn })
                    assertValueAt(2, { isLoggedIn })
                    valueAt(3, { assertFrontDoor({ this is Door.Open }) })
                    valueAt(4, { assertFrontDoor({ this is Door.Closed }) })
                    assertValueAt(5, { isNotLoggedIn })
                }

        state.login("test", "123")
        state.openDoor(Location.FRONT)
        state.closeDoor(Location.FRONT)
        state.logout()
    }

    private fun User.assertFrontDoor(predicate: Door.() -> Boolean) = let { user ->
        when (user) {
            is User.LoggedIn -> user
                    .house
                    .doors
                    .first { it.location === Location.FRONT }
                    .let(predicate)
                    .let(::assert)
            else -> assert(false)
        }
    }

}