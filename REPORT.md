# Report: MVI vs MVVM - Stateful vs Stateless Comparison (Revised)

## 1. Hypothesis

> **In this project's simplified implementations, making an MVVM design stateless (single source of truth, unidirectional flow) yields an MVI-style design.**

The implementations in this project support this scoped hypothesis. Below we compare state handling, data flow, and testing across Haskell and Kotlin, while distinguishing project-specific observations from broader claims.

---

## 2. Haskell: MVI vs MVVM

### 2.1 Haskell MVI

- **Model**: Single immutable value (`Model`); the only state.
- **Intent**: Sum type (`SetGreeting String | Reset`); all user actions are explicit.
- **Reducer**: Pure function `reduce :: Intent -> Model -> Model`. No IO, no hidden state.
- **View**: Pure `view :: Model -> String`. Rendering depends only on the model.

**Stateless in this sense**: The app state is the current `Model`. There is no separate mutable store; each new state is computed from the previous state and an intent. The same `(initialModel, list of intents)` always yields the same final model, so the core is reproducible and easy to test.

### 2.2 Haskell MVVM

- **ViewModel**: Simulated with an `IORef ViewModel`. The ViewModel is the stateful object; the view reads from it.
- **Command**: Same shape as Intent (`SetGreeting | Reset`), applied imperatively: `runCommand ref cmd` reads the ref, applies a pure transition (`applyCommand`), and writes back.
- **Stateful in this sense**: The current state lives in the `IORef`; multiple code paths could mutate it (even though this example keeps updates disciplined).

The **pure transition** `applyCommand :: Command -> ViewModel -> ViewModel` is the same shape as MVI's `reduce`. So in Haskell:

- **MVVM** = stateful shell (`IORef`) + pure transition function.
- **MVI** = no stateful shell; pure transition + explicit current model passed through (for example via `fold`).

**Making Haskell MVVM stateless**: If we remove the `IORef` and drive updates by folding intents over an initial model, we get the MVI style in this project.

---

## 3. Kotlin: MVVM vs MVI

### 3.1 Kotlin MVVM

- **ViewModel**: `HelloViewModel` holds `MutableStateFlow<UiState>`. View observes `state: StateFlow<UiState>`.
- **Commands**: Methods `setGreeting(text)` and `reset()` update `_state.value` imperatively.
- **Stateful**: The ViewModel is the state holder, and state updates occur in methods.

### 3.2 Kotlin MVI

- **State**: Immutable `State` (for example `data class State(val greeting: String)`), single source of truth.
- **Intent**: Sealed interface `SetGreeting(text) | Reset`, representing all user actions.
- **Reducer**: Pure function `reduce(intent, state): State`. No side effects; same inputs imply same output.
- **View**: Renders from `State` only (for example `view(state) = state.greeting`).

**Making Kotlin MVVM stateless**: If we (1) stop mutating state inside the ViewModel, (2) use a single immutable state and one transition function `(Intent, State) -> State`, and (3) render only from state, we reach the Kotlin MVI version in this repo.

---

## 4. Cross-Language Comparison

### 4.1 Haskell MVI vs Kotlin MVVM

| Aspect | Haskell MVI | Kotlin MVVM |
|--------|-------------|-------------|
| **State** | Single immutable `Model` | `StateFlow<UiState>` (observable mutable holder) |
| **Updates** | Pure `reduce(intent, model)` | Imperative methods mutate `_state` |
| **Flow** | Unidirectional: Intent -> Model -> View | View -> ViewModel methods -> state update -> View observes |
| **Testability** | Reducer and view are pure; no mocks needed | In this repo, tests use a ViewModel instance and coroutine scope; in general this depends on API/testing style |
| **Side effects** | Isolated in `main` (or an effect layer); core is pure | Side effects can be mixed with state updates in MVVM methods, although this repo's methods are pure state updates |

Haskell MVI keeps the entire core (model, intents, reducer, view) pure and pushes effects to the edges. Kotlin MVVM here is stateful in structure; testing and reasoning depend on ViewModel API discipline and chosen testing approach.

### 4.2 Haskell MVI vs Kotlin MVI

| Aspect | Haskell MVI | Kotlin MVI |
|--------|-------------|------------|
| **State** | Immutable `Model` | Immutable `State` data class |
| **Reducer** | Pure `reduce :: Intent -> Model -> Model` | Pure `reduce(Intent, State): State` |
| **Intent** | Sum type | Sealed interface |
| **Expressiveness** | Types directly model intent-driven transitions | Same core idea via sealed classes/interfaces |
| **Testing** | Test reducer and view without IO | Test reducer without ViewModel or coroutine machinery |

Conceptually they match: single state, explicit intents, pure reducer, view from state. The main difference is language defaults (purity/immutability by default in Haskell vs explicit architectural discipline in Kotlin).

---

## 5. Summary Table

| Implementation | State handling | Flow | Core testability |
|----------------|----------------|------|-------------------|
| **Haskell MVI** | Immutable model; no hidden state | Unidirectional (Intent -> Model -> View) | Pure reducer/view; trivial to test |
| **Haskell MVVM** | `IORef` ViewModel (stateful shell) | ViewModel commands update ref; view reads | Pure `applyCommand` testable; IO shell isolated |
| **Kotlin MVVM** | `MutableStateFlow` in ViewModel | View -> ViewModel -> state -> View | State-holder tests in this repo use `runBlocking`; still testable |
| **Kotlin MVI** | Immutable state | Unidirectional (Intent -> reduce -> State -> View) | Pure reducer; trivial to test |

---

## 6. Conclusion

- **Scoped hypothesis confirmed**: In both Haskell and Kotlin examples in this repo, turning MVVM into a stateless, unidirectional design (single source of truth, pure reducer transitions, view from state) produces an MVI-style architecture.
- **MVI** emphasizes one state, explicit intents, pure transitions, and view as a function of state (stateless core, effects at boundaries).
- **MVVM** emphasizes a stateful ViewModel observed by the view; when that model is constrained to reducer-style transitions, the core behavior converges toward MVI.

The code in this repo illustrates this convergence and the trade-offs between stateful MVVM shells and stateless/unidirectional MVI-style cores.

---

## 7. Scope Notes (Avoiding Overgeneralization)

- This report compares intentionally minimal examples, not full production framework usage.
- The equivalence claim is about **state-transition core shape** (`event + state -> new state`), not complete framework ergonomics.
- Kotlin MVVM tests here use coroutines (`runBlocking` + `StateFlow.first()`), but other MVVM codebases may test synchronously depending on exposed API and tooling.
- Side effects are not present in the provided Kotlin MVVM methods; side-effect mixing is an architectural risk surface, not an observed issue in this repository.
