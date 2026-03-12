-- | MVVM (Model-View-ViewModel): ViewModel holds state; View observes it.
-- Stateful: ViewModel is the mutable holder; commands update it imperatively.

module App where

import Data.IORef (IORef, newIORef, readIORef, writeIORef)

-- | ViewModel state (what the View observes).
data ViewModel = ViewModel
  { greeting :: String
  }
  deriving (Eq, Show)

initialViewModel :: ViewModel
initialViewModel = ViewModel "Hello, World!"

-- | Command: user-triggered actions (analogous to ViewModel methods).
data Command
  = SetGreeting String
  | Reset
  deriving (Eq, Show)

-- | Apply a command to a ViewModel value (pure transition for testing).
applyCommand :: Command -> ViewModel -> ViewModel
applyCommand (SetGreeting s) vm = vm { greeting = s }
applyCommand Reset _ = initialViewModel

-- | View: render ViewModel to string.
view :: ViewModel -> String
view vm = greeting vm

-- | Run commands in IO against an IORef (stateful ViewModel simulation).
runCommand :: IORef ViewModel -> Command -> IO ()
runCommand ref cmd = do
  current <- readIORef ref
  writeIORef ref (applyCommand cmd current)

-- | Create initial ViewModel in IO.
newViewModel :: IO (IORef ViewModel)
newViewModel = newIORef initialViewModel
