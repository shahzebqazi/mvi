-- | MVI (Model-View-Intent): unidirectional, stateless core.
-- Intent → Model (single source of truth) → View.
-- State is only updated by applying intents via a pure reducer.

module App where

-- | Model: single source of truth (the only "state").
data Model = Model
  { greeting :: String
  }
  deriving (Eq, Show)

initialModel :: Model
initialModel = Model "Hello, World!"

-- | Intent: user/outside actions (events).
data Intent
  = SetGreeting String
  | Reset
  deriving (Eq, Show)

-- | Reducer: pure transition (Intent, Model) -> Model.
-- Stateless in the sense: no hidden mutable state; output is a function of inputs.
reduce :: Intent -> Model -> Model
reduce (SetGreeting s) m = m { greeting = s }
reduce Reset _ = initialModel

-- | View: render model to a string (simulated UI).
view :: Model -> String
view m = greeting m

-- | Run a list of intents from initial model (for testing / REPL).
runIntents :: [Intent] -> Model
runIntents = foldl (flip reduce) initialModel
