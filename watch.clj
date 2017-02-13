(require '[cljs.build.api :as b])

(def options
  {:main 'testproject.core
   :output-to "out/result.js"
   :output-dir "out/result"
   :target :nodejs
   :optimizations :none
   :pretty-print true
   :language-in  :ecmascript5
   :language-out :ecmascript5
   :verbose true})

(b/watch (b/inputs "src" "assets") options)
