package io.lamart.glyph.operators

import io.lamart.glyph.implementation.SimpleGlyph
import org.junit.Assert.*
import org.junit.Test

class CheckTests {

    sealed class User {
        object NotLoggedIn : User()
        data class LoggedIn(val name: String) : User()
    }

    @Test
    fun test() {
        val glyph = SimpleGlyph<User>(User.NotLoggedIn)
        val loggedInGlyph = glyph.check<User.LoggedIn>({ it is User.LoggedIn })

        loggedInGlyph.get().let(::assertNull)
        loggedInGlyph.set(User.LoggedIn("Danny"))
        loggedInGlyph.get().let(::assertNotNull)
        glyph.set(User.NotLoggedIn)
        loggedInGlyph.get().let(::assertNull)
    }

}