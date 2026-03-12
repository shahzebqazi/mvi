package mvvm

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val viewModel = HelloViewModel()
    println("MVVM View: ${viewModel.state.first().greeting}")
    viewModel.setGreeting("Hello, MVVM!")
    println("After SetGreeting: ${viewModel.state.first().greeting}")
    viewModel.reset()
    println("After Reset: ${viewModel.state.first().greeting}")
}
