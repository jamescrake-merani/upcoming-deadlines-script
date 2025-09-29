(ns jamescrake-merani.upcoming-deadlines-script
  (:require [dk.ative.docjure.spreadsheet :as ss])
  (:import (java.text SimpleDateFormat))
  (:gen-class))

(def date-format (SimpleDateFormat/new "dd/MM/yyyy"))

(defn interpret-date [date-str]
  (SimpleDateFormat/.parse date-format date-str))

(defn load-assessment-calendar [filename]
  (->> (ss/load-workbook filename)
       (ss/select-sheet "Sheet1")
       (ss/select-columns
        {:A :module-code
         :B :module-name
         :C :assessment-type
         :D :assessment-name
         :E :percentage
         :F :hand-out-date
         :G :hand-in-date
         :H :feedback-date})))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
