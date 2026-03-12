# MVI vs MVVM: Hello World and Stateful/Stateless Comparison

Minimal **Hello World** implementations in **MVVM** and **MVI** in **Haskell** and **Kotlin**, plus a comparison report and tests. The project explores the hypothesis that **making MVVM stateless and unidirectional yields MVI**.

## Structure

```
MVI/
├── PROJECT_PROMPT.md     # Project specification and prompt
├── README.md             # This file
├── REPORT.md             # Comparison report (MVI vs MVVM, Haskell vs Kotlin)
├── haskell/
│   ├── mvi/              # Haskell MVI (unidirectional, intent-driven)
│   └── mvvm/             # Haskell MVVM (ViewModel-style state)
└── kotlin/
    ├── mvvm/             # Kotlin MVVM (ViewModel + StateFlow)
    └── mvi/              # Kotlin MVI (Intent → State → View)
```

## Quick start

### Haskell (MVI and MVVM)

**Requirements:** GHC 9.x and Cabal, or [GHCup](https://www.haskell.org/ghcup/).

```bash
# MVI
cd haskell/mvi && cabal run mvi

# MVVM
cd haskell/mvvm && cabal run mvvm
```

**Tests:**

```bash
cd haskell/mvi && cabal test
cd haskell/mvvm && cabal test
```

### Kotlin (MVVM and MVI)

**Requirements:** JDK 17+. Gradle 8.x (or use the wrapper: create it with `gradle wrapper` in each Kotlin subproject if needed).

```bash
# Create wrapper (once, if you have Gradle installed):
cd kotlin/mvvm && gradle wrapper
cd ../mvi && gradle wrapper

# MVVM — run and test
cd kotlin/mvvm
./gradlew run
./gradlew test

# MVI — run and test
cd kotlin/mvi
./gradlew run
./gradlew test
```

To generate the Gradle wrapper (gradlew) if needed: install [Gradle](https://gradle.org/install/) or use SDKMAN (`sdk install gradle`), then run `gradle wrapper` in `kotlin/mvvm` and `kotlin/mvi`. After that, `./gradlew run` and `./gradlew test` work in each directory.

## What each variant does

- **Hello World**: Each app displays a greeting (e.g. `"Hello, World!"`).
- **Optional actions**: Some variants include one or two intents/actions (e.g. set greeting text, reset) to show how state and intents flow in MVVM vs MVI.

## Report and hypothesis

See [REPORT.md](REPORT.md) for:

- Comparison of **stateful vs stateless** in these examples
- **MVVM vs MVI** in Haskell and Kotlin
- **Haskell MVI vs Kotlin MVVM/MVI**
- Whether “MVVM made stateless” aligns with MVI

## Tests

- **Unit tests**: Reducers, ViewModel/state updates, intent handling.
- **Acceptance tests**: End-to-end behavior (Hello World and optional actions) for each implementation.

Run tests per subproject (see Quick start above).

## License

MIT (or as you prefer).
