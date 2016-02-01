# err

[TODO: irrelevant quote with word err]

err is a Clojure library for error handling.

Motivation you can find [here](https://s-mage.github.io/2015/10/16/better-errors.html) 
and following links in the post, especially
[this one](https://www.niwi.nz/2015/03/08/error-handling/).

Cats is good enough in monadic errors handling, this library is mostly
a couple of helpers to better interact with foreign libs (that are likely
know nothing about all that monads).

## Usage

Right value:

    (ok 42) ; #<Right 42>

Functions that return `#<Left "msg">`:

    (fail "msg")          ; #<Left "msg">
    (fail-nil "msg" nil)  ; #<Left "msg">
    (fail-empty "msg" []) ; #<Left "msg">
    (fail-zero "msg" 0)   ; #<Left "msg">
    (fail-ex / 1 0)       ; #<Left "Divide by zero">

    (fail-nil "msg" 42)     ; #<Right 42>
    (fail-empty "msg" [42]) ; #<Right [42]>
    (fail-zero "msg" 42)    ; #<Right 42>
    (fail-ex + 33 9)        ; 42

Check if value is failed:

    (failed? (ok 1))   ; false
    (failed? (fail 1)) ; true
    (failed? 1)        ; false

Run function if none of arguments are failed:

    (either + 2 3)                ; 5
    (either + (ok 3) (ok 2))      ; 5
    (either + (ok 3) (fail "eh")) ; #<Left "eh">

Same in threading macro:

    (either->> 3 #(+ 2 %) #(* 2 %)) ; 10

Let analogue:

    (either-let [a (ok 2) b (inc a)] (+ a b))        ; 3
    (either-let [a (ok 2) b (fail "eh")] (+ a b))    ; #<Left "eh">
    (either-let [a (ok 2) b (fail "eh")] (+ a b) 42) ; 42

## License

Copyright © 2016 Sergey Smagin

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
