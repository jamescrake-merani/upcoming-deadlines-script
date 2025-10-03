(ns jamescrake-merani.upcoming-deadlines-script
  (:require [dk.ative.docjure.spreadsheet :as ss]
            [selmer.parser :as selmer]
            [selmer.filters :as filters]
            [clojure.java.io :as io]
            [babashka.cli :as cli])
  (:import (java.text SimpleDateFormat))
  (:gen-class))

;; TODO: This can be configurable later.
(def interested-modules
  #{"CM3104"
    "CM3107"
    "CM3109"
    "CM3110"
    "CM3111"
    "CM3112"
    "CM3113"
    "CM3114"
    "CM3116"
    "CM3117"
    "CM3118"
    "CM3202"
    "CM3203"})

(defn combine-assessments [modules]
  (map (fn [group-module]
         (let [reference-module (first (second group-module))]
           {:module-code (first group-module)
            :module-name (:module-name reference-module)
            :assessments (map (fn [individual-module]
                                (dissoc individual-module
                                        :module-code
                                        :module-name))
                              (second group-module))
            }))
       (group-by #(:module-code %) modules)))

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
         :H :feedback-date})
       (filter #(contains? interested-modules (:module-code %)))
       (combine-assessments)))

(defn assessment-is-exam? [assessment]
  (or (nil? (:hand-in-date assessment))
      (nil? (:hand-out-date assessment))))
(filters/add-filter! assessment-is-exam?)

(defn generate-assessment-text [filename]
  (selmer/render-file
   "template.md"
   {:modules (load-assessment-calendar filename)}))

(comment
  (selmer/render-file "template.md" {:modules (load-assessment-calendar "/var/home/james/Downloads/Assessment Calendar.xlsx")}))

(def cli-spec
  {:spec
   {:spreadsheet {:desc "Spreadsheet of the assessment calendar."
                  :alias :s
                  :validate #(.exists (io/file %))}}})

(defn -main
  [& args]
  (let [opts (cli/parse-opts args cli-spec)]
    (println (generate-assessment-text (:spreadsheet opts)))))
