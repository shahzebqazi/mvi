-- | Acceptance: same scenario as Main — initial view, SetGreeting, Reset; assert final view.
module Acceptance where

import App (Intent (Reset, SetGreeting), initialModel, reduce, view)

-- | Scenario: user sees initial greeting, then SetGreeting "Hello, MVI!", then Reset.
acceptanceScenario :: Bool
acceptanceScenario =
  let m0 = initialModel
      v0 = view m0
      m1 = reduce (SetGreeting "Hello, MVI!") m0
      v1 = view m1
      m2 = reduce Reset m1
      v2 = view m2
  in v0 == "Hello, World!"
      && v1 == "Hello, MVI!"
      && v2 == "Hello, World!"
