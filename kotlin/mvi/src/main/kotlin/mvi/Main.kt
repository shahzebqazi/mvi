package mvi

fun main() {
    var state = Defaults.initialState
    println("MVI View: ${view(state)}")
    state = reduce(Intent.SetGreeting("Hello, MVI!"), state)
    println("After SetGreeting: ${view(state)}")
    state = reduce(Intent.Reset, state)
    println("After Reset: ${view(state)}")
}
