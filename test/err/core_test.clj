(ns err.core-test
  (:require [clojure.test :refer :all]
            [err.core :refer :all]))

(deftest no-error-test
  (is (= 5 (either #(+ 3 %) 2))
      "Should not stop evaluation if args are valid"))

(deftest simple-error-test
  (is (= (fail "pew") (either #(+ 3 %) (fail "pew")))
      "Should fail if arg failed"))

(deftest simple-error-type
  (is (= true (failed? (either #(+ 3 %) (fail "pew"))))
      "Should confirm that failed is failed"))

(deftest multi-argument-correct
  (is (= 5 (either + 2 3))
      "Should work with multiple-argument functions"))

(deftest multi-argument-right
  (is (= 5 (either + 2 (ok 3)))
      "Should work with either values too"))

(deftest multi-argument-fail
  (is (= (fail "pew") (either + 2 (fail "pew")))
      "Should fail if one of args failed"))

(deftest thread-either-correct
  (is (= 1 (either->> 3 #(+ 2 %) #(/ 5 %)))
      "Should work well with chain"))

(deftest thread-either-fail
  (is (= (fail "arg: 3") (either->> 3 #(fail (str "arg: " %)) #(/ 5 %)))
      "Should traverse error if one function in chain failed"))

(deftest either-let-correct
  (is (= 2 (either-let [a (ok 1)
                        b (inc a)] b))
      "Should work when no error happened"))

(deftest either-let-fail
  (is (= (fail "oops") (either-let [a (fail "oops")
                                    b (inc a)]
                                   b))
      "Should return failed arg when error happened"))

(deftest either-let-fail-default
  (is (= ":(" (either-let [a (fail "oops")
                           b (inc a)]
                          b ":("))
      "Should return default arg when error happened"))
