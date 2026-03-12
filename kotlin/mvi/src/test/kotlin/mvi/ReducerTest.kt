package mvi

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReducerTest {

    @Test
    fun `initial state view is Hello World`() {
        assertEquals(Defaults.INITIAL_GREETING, view(Defaults.initialState))
    }

    @Test
    fun `SetGreeting intent updates state`() {
        val newState = reduce(Intent.SetGreeting("Hi"), Defaults.initialState)
        assertEquals("Hi", newState.greeting)
    }

    @Test
    fun `Reset intent restores initial state`() {
        val afterSet = reduce(Intent.SetGreeting("Other"), Defaults.initialState)
        val afterReset = reduce(Intent.Reset, afterSet)
        assertEquals(Defaults.INITIAL_GREETING, afterReset.greeting)
    }

    @Test
    fun `reducer is pure - multiple intents chain`() {
        val s = listOf(
            Intent.SetGreeting("A"),
            Intent.SetGreeting("B"),
            Intent.Reset
        ).fold(Defaults.initialState) { acc, intent -> reduce(intent, acc) }
        assertEquals(Defaults.INITIAL_GREETING, s.greeting)
    }

    @Test
    fun `SetGreeting returns a new state and leaves previous untouched`() {
        val original = Defaults.initialState
        val updated = reduce(Intent.SetGreeting("Changed"), original)
        assertEquals(Defaults.INITIAL_GREETING, original.greeting)
        assertEquals("Changed", updated.greeting)
    }

    @Test
    fun `run sequence without reset keeps latest greeting`() {
        val finalState = listOf(
            Intent.SetGreeting("First"),
            Intent.SetGreeting("Second"),
            Intent.SetGreeting("Final")
        ).fold(Defaults.initialState) { acc, intent -> reduce(intent, acc) }
        assertEquals("Final", finalState.greeting)
    }

    @Test
    fun `reset is idempotent`() {
        val afterSet = reduce(Intent.SetGreeting("X"), Defaults.initialState)
        val once = reduce(Intent.Reset, afterSet)
        val twice = reduce(Intent.Reset, once)
        assertEquals(Defaults.initialState, once)
        assertEquals(Defaults.initialState, twice)
    }

    @Test
    fun `SetGreeting accepts empty string as explicit state`() {
        val newState = reduce(Intent.SetGreeting(""), Defaults.initialState)
        assertEquals("", newState.greeting)
    }
}
