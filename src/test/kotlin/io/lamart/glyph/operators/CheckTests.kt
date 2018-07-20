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

        loggedInGlyph.get()?.name?.let(::assertNull)
        loggedInGlyph.set(User.LoggedIn("Danny"))
        loggedInGlyph.get()?.name.let(::assertNotNull)
        glyph.set(User.NotLoggedIn)
        loggedInGlyph.get()?.name?.let(::assertNull)
    }

    @Test
    fun testOptional() {
        val glyph = SimpleGlyph<User>(User.NotLoggedIn).toOptional()
        val loggedInGlyph = glyph.check<User.LoggedIn>({ it is User.LoggedIn })

        loggedInGlyph.get()?.name?.let(::assertNull)
        loggedInGlyph.set(User.LoggedIn("Danny"))
        loggedInGlyph.get()?.name.let(::assertNotNull)
        glyph.set(User.NotLoggedIn)
        loggedInGlyph.get()?.name?.let(::assertNull)
    }

}