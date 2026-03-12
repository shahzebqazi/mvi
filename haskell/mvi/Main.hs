module Main where

import App (Intent (Reset, SetGreeting), initialModel, reduce, view)

main :: IO ()
main = do
  let model = initialModel
  putStrLn $ "MVI View: " ++ view model
  let model' = reduce (SetGreeting "Hello, MVI!") model
  putStrLn $ "After SetGreeting: " ++ view model'
  let model'' = reduce Reset model'
  putStrLn $ "After Reset: " ++ view model''
