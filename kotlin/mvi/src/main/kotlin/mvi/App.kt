package mvi

/**
 * MVI: Model (State) is single source of truth; Intent → reduce → new State; View renders State.
 * Stateless core: reducer is pure (Intent, State) -> State.
 */
data class State(val greeting: String)

sealed interface Intent {
    data class SetGreeting(val text: String) : Intent
    data object Reset : Intent
}

object Defaults {
    const val INITIAL_GREETING = "Hello, World!"
    val initialState = State(INITIAL_GREETING)
}

fun reduce(intent: Intent, state: State): State = when (intent) {
    is Intent.SetGreeting -> state.copy(greeting = intent.text)
    is Intent.Reset -> Defaults.initialState
}

fun view(state: State): String = state.greeting
