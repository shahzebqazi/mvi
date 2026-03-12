module Main where

import App
  ( Command (Reset, SetGreeting),
    ViewModel (ViewModel),
    applyCommand,
    initialViewModel,
    newViewModel,
    runCommand,
    view,
  )
import Data.IORef (readIORef)

main :: IO ()
main = do
  -- Unit: initial ViewModel
  assert (view initialViewModel == "Hello, World!") "initial view"
  -- Unit: applyCommand (pure)
  let vm1 = applyCommand (SetGreeting "Hi") initialViewModel
  assert (view vm1 == "Hi") "SetGreeting"
  let vm2 = applyCommand Reset vm1
  assert (view vm2 == "Hello, World!") "Reset"
  -- Acceptance: full scenario (same as Main)
  let vm3 = applyCommand (SetGreeting "Hello, MVVM!") initialViewModel
  let vm4 = applyCommand Reset vm3
  assert (view vm4 == "Hello, World!") "acceptance scenario"
  -- Unit: multiple updates keep latest value
  let vm5 =
        applyCommand
          (SetGreeting "Final")
          (applyCommand (SetGreeting "Second") (applyCommand (SetGreeting "First") initialViewModel))
  assert (view vm5 == "Final") "latest SetGreeting wins"
  -- Unit: reset is idempotent
  let vm6 = applyCommand Reset (applyCommand Reset vm1)
  assert (vm6 == initialViewModel) "reset idempotent"
  -- Unit: allow explicit empty greeting value
  let vm7 = applyCommand (SetGreeting "") initialViewModel
  assert (view vm7 == "") "empty greeting"
  -- Stateful path: runCommand mutates IORef as expected
  ref <- newViewModel
  runCommand ref (SetGreeting "From IORef")
  vmRef1 <- readIORef ref
  assert (view vmRef1 == "From IORef") "runCommand SetGreeting"
  runCommand ref Reset
  vmRef2 <- readIORef ref
  assert (view vmRef2 == "Hello, World!") "runCommand Reset"
  putStrLn "All MVVM tests passed."

assert :: Bool -> String -> IO ()
assert True _ = return ()
assert False msg = error $ "Assertion failed: " ++ msg
