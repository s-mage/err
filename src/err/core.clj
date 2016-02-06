(ns err.core
  (:require [cats.builtin]
            [cats.core :refer [alet]]
            [cats.monad.either :as ei]))

(def fail ei/left)
(def ok ei/right)

(defn value [arg]
  (if (ei/either? arg) @arg arg))

(defn fail-nil [message arg]
  (let [v (value arg)]
    (if v (ok v) (fail message))))

(defn fail-empty [message arg]
  (let [v (value arg)]
    (if (empty? v) (fail message) (ok v))))

(defn fail-zero [message arg]
  (let [v (value arg)]
    (if (= 0 v) (fail message) (ok v))))

(defn fail-ex [f & args]
  (try (apply f args)
       (catch Exception e (fail (.toString e)))))

(defmacro fail-ex! [& body]
  `(try ~@body (catch Exception e# (fail (.toString e#)))))

(def failed? ei/left?)

(defn failed-arg [args]
  (first (filter failed? args)))

(defn either [f & args]
  (if-let [x (failed-arg args)] x (apply f (map value args))))

(defmacro either->> [val & fns]
  (let [fns (for [f fns] `(either ~f))]
    `(->> ~val ~@fns)))

(defmacro either-let
  ([bindings return] `(alet ~bindings ~return))
  ([bindings return fallback]
   `(let [result# (alet ~bindings ~return)]
     (if (failed? result#) ~fallback result#))))
