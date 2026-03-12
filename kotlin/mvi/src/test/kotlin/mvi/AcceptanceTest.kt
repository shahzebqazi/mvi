package mvi

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Acceptance: same scenario as Main — initial view, SetGreeting, Reset; assert final state.
 */
class AcceptanceTest {

    @Test
    fun `full flow matches Main scenario`() {
        var state = Defaults.initialState
        assertEquals(Defaults.INITIAL_GREETING, view(state))
        state = reduce(Intent.SetGreeting("Hello, MVI!"), state)
        assertEquals("Hello, MVI!", view(state))
        state = reduce(Intent.Reset, state)
        assertEquals(Defaults.INITIAL_GREETING, view(state))
    }
}
