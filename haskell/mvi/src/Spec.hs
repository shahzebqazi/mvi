module Main where

import App
  ( Intent (Reset, SetGreeting),
    initialModel,
    Model (Model),
    greeting,
    reduce,
    runIntents,
    view,
  )
import Acceptance (acceptanceScenario)

main :: IO ()
main = do
  -- Unit: initial model
  assert (view initialModel == "Hello, World!") "initial view"
  -- Unit: reducer
  let m1 = reduce (SetGreeting "Hi") initialModel
  assert (greeting m1 == "Hi") "SetGreeting"
  let m2 = reduce Reset m1
  assert (greeting m2 == "Hello, World!") "Reset"
  -- Unit: runIntents
  let m3 = runIntents [SetGreeting "A", SetGreeting "B", Reset]
  assert (greeting m3 == "Hello, World!") "runIntents"
  -- Unit: runIntents with empty input keeps initial model
  let m4 = runIntents []
  assert (m4 == initialModel) "runIntents empty"
  -- Unit: no reset means latest SetGreeting wins
  let m5 = runIntents [SetGreeting "First", SetGreeting "Second", SetGreeting "Final"]
  assert (greeting m5 == "Final") "runIntents latest wins"
  -- Unit: reset is idempotent
  let m6 = reduce Reset (reduce Reset m1)
  assert (m6 == initialModel) "reset idempotent"
  -- Unit: reducer does not alter previous model value
  let _m7 = reduce (SetGreeting "Changed") initialModel
  assert (greeting initialModel == "Hello, World!") "initial model unchanged"
  -- Unit: allow explicit empty greeting value
  let m8 = reduce (SetGreeting "") initialModel
  assert (greeting m8 == "") "empty greeting"
  -- Acceptance: full scenario (same as Main)
  assert acceptanceScenario "acceptance scenario"
  putStrLn "All MVI tests passed."

assert :: Bool -> String -> IO ()
assert True _ = return ()
assert False msg = error $ "Assertion failed: " ++ msg
