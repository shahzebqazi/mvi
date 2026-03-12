# Project Prompt: MVI vs MVVM — Hello World and Stateful/Stateless Transformation

## Objective

Build a minimal **Hello World** in two architectural styles—**MVVM** and **MVI**—in both **Haskell** and **Kotlin**. Use the project to test the hypothesis:

> **Making an MVVM design stateless (single source of truth, unidirectional flow) yields an MVI-style design.**

## Definitions (working)

- **MVVM (Model–View–ViewModel)**  
  - View observes a **stateful ViewModel**.  
  - ViewModel holds and updates state; View binds to it (data binding / observables).  
  - Flow: User → View → ViewModel (commands/events) → ViewModel updates state → View reflects state.

- **MVI (Model–View–Intent)**  
  - **Unidirectional**: User **Intents** → **Model** (single source of truth) → **View** renders model.  
  - Often **stateless** in the sense: “state” is the current **Model**; it is only updated by applying **Intents** (or events) in a pure way (e.g. `model' = reduce(intent, model)`).

- **Stateful vs Stateless**  
  - **Stateful**: Component keeps mutable state; multiple ways to change it; harder to reason about.  
  - **Stateless**: No hidden mutable state; output is a pure function of inputs (e.g. current model + intent); reproducible and testable.

## Tasks

1. **Haskell**
   - Implement **Hello World** in **MVI**: unidirectional, intent-driven, “state” = current model, updated only by intents.
   - Implement **Hello World** in **MVVM**: ViewModel-like component holding state; view reads from it.
   - Compare: how does making the Haskell “MVVM” more stateless and unidirectional approach the Haskell MVI?

2. **Kotlin**
   - Implement **Hello World** in **MVVM**: ViewModel, observable state (e.g. StateFlow/LiveData), View observes.
   - Transform / reimplement the same behavior as **MVI**: single state, intents, reducer, View renders state.
   - Compare: what changes when going from Kotlin MVVM to Kotlin MVI (state handling, testability, flow)?

3. **Cross-language comparison**
   - Compare **Haskell MVI** with **Kotlin MVVM** and **Kotlin MVI**: expressiveness, state handling, side effects, testing.

## Functional scope (Hello World)

- **Minimal behavior**: display a greeting (e.g. “Hello, World!”).
- **Optional small extension**: one or two intents/actions (e.g. “Set greeting to X”, “Reset”), to show how state/intent flow differs between MVVM and MVI.

## Deliverables

| Deliverable        | Description |
|--------------------|-------------|
| **Kotlin MVVM**    | Hello World (and optional actions) implemented in Kotlin with MVVM. |
| **Kotlin MVI**     | Same behavior in Kotlin with MVI (intents, single state, reducer). |
| **Haskell MVI**    | Hello World (and optional intents) in Haskell with MVI-style unidirectional flow. |
| **Haskell MVVM**   | Same in Haskell with an MVVM-style stateful “ViewModel”. |
| **Report**         | Short comparison: stateful vs stateless, MVVM vs MVI, Haskell vs Kotlin; validate or refine the hypothesis. |
| **Unit tests**     | Unit tests for core logic (reducers, ViewModel updates, intent handling). |
| **Acceptance tests** | End-to-end or acceptance tests that verify Hello World (and optional actions) for each implementation. |
| **README**         | How to build, run, and test each variant; pointer to report and structure. |

## Success criteria

- All four implementations (Haskell MVI, Haskell MVVM, Kotlin MVVM, Kotlin MVI) run and satisfy the same functional spec (Hello World ± small actions).
- Report clearly explains how “MVVM made stateless/unidirectional” relates to MVI, with references to the code.
- Unit and acceptance tests are present and runnable via standard tooling (e.g. `stack test` / `cabal test`, Gradle).

## Suggested structure

```
MVI/
├── PROJECT_PROMPT.md     # This prompt
├── README.md
├── REPORT.md             # Comparison report
├── haskell/
│   ├── mvi/              # Haskell MVI
│   └── mvvm/             # Haskell MVVM
└── kotlin/
    ├── mvvm/             # Kotlin MVVM
    └── mvi/              # Kotlin MVI
```

Each subproject should have its own build and test instructions (and tests in the same tree).
