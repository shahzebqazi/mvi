package mvvm

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Acceptance: same scenario as Main — initial view, SetGreeting, Reset; assert final state.
 */
class AcceptanceTest {

    @Test
    fun `full flow matches Main scenario`() = runBlocking {
        val vm = HelloViewModel()
        assertEquals(Defaults.INITIAL_GREETING, vm.state.first().greeting)
        vm.setGreeting("Hello, MVVM!")
        assertEquals("Hello, MVVM!", vm.state.first().greeting)
        vm.reset()
        assertEquals(Defaults.INITIAL_GREETING, vm.state.first().greeting)
    }
}
