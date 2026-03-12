package mvvm

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * MVVM: ViewModel holds state; View observes via StateFlow.
 * Stateful: mutable state inside ViewModel, updated by commands.
 */
data class UiState(val greeting: String)

object Defaults {
    const val INITIAL_GREETING = "Hello, World!"
}

class HelloViewModel {
    private val _state = MutableStateFlow(UiState(Defaults.INITIAL_GREETING))
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun setGreeting(text: String) {
        _state.value = _state.value.copy(greeting = text)
    }

    fun reset() {
        _state.value = UiState(Defaults.INITIAL_GREETING)
    }
}
