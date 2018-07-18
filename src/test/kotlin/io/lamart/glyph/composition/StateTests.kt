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
                    valueAt(3, { assertFrontDoor({ isOpen }) })
                    valueAt(4, { assertFrontDoor({ !isOpen }) })
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