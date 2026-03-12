package mvvm

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ViewModelTest {

    @Test
    fun `initial state is Hello World`() = runBlocking {
        val vm = HelloViewModel()
        assertEquals(Defaults.INITIAL_GREETING, vm.state.first().greeting)
    }

    @Test
    fun `setGreeting updates state`() = runBlocking {
        val vm = HelloViewModel()
        vm.setGreeting("Hi")
        assertEquals("Hi", vm.state.first().greeting)
    }

    @Test
    fun `reset restores initial greeting`() = runBlocking {
        val vm = HelloViewModel()
        vm.setGreeting("Other")
        vm.reset()
        assertEquals(Defaults.INITIAL_GREETING, vm.state.first().greeting)
    }

    @Test
    fun `multiple setGreeting calls keep latest value`() = runBlocking {
        val vm = HelloViewModel()
        vm.setGreeting("First")
        vm.setGreeting("Second")
        vm.setGreeting("Final")
        assertEquals("Final", vm.state.first().greeting)
    }

    @Test
    fun `reset is idempotent`() = runBlocking {
        val vm = HelloViewModel()
        vm.setGreeting("Changed")
        vm.reset()
        val once = vm.state.first()
        vm.reset()
        val twice = vm.state.first()
        assertEquals(UiState(Defaults.INITIAL_GREETING), once)
        assertEquals(UiState(Defaults.INITIAL_GREETING), twice)
    }

    @Test
    fun `setGreeting accepts empty string`() = runBlocking {
        val vm = HelloViewModel()
        vm.setGreeting("")
        assertEquals("", vm.state.first().greeting)
    }
}
