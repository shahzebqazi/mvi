module Main where

import App
import Data.IORef (readIORef)

main :: IO ()
main = do
  ref <- newViewModel
  vm0 <- readIORef ref
  putStrLn $ "MVVM View: " ++ view vm0
  runCommand ref (SetGreeting "Hello, MVVM!")
  vm1 <- readIORef ref
  putStrLn $ "After SetGreeting: " ++ view vm1
  runCommand ref Reset
  vm2 <- readIORef ref
  putStrLn $ "After Reset: " ++ view vm2
