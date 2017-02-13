# ClojureScript Compiler Playground #


## Foreign Libs Regression ##

The compiler output using the `r1.9.456` cljs compiler (striped output):

```bash
$ java -cp ../clojurescript/target/cljs.jar:src:assets clojure.main build.clj
Building ...
[...]
Copying file:/home/niwi/tmp/cljs-playground/assets/_rxjs/rx.js to out/result/assets/_rxjs/rx.js
[...]
... done. Elapsed 0.92150486 seconds
Watching paths: /home/niwi/tmp/cljs-playground/src, /home/niwi/tmp/cljs-playground/assets
```

The resulting script executes as expected:

```bash
$ node out/result.js
Hello world
```


The same project and the same code when is compiled using `r1.9.473` or `master`
dups the following output:

```bash
$ java -cp ../clojurescript/target/cljs.jar:src:assets clojure.main build.clj
Building ...
[...]
Copying file:/home/niwi/tmp/cljs-playground/assets/_rxjs/rx.js to out/result/_rxjs/rx.js
[...]
... done. Elapsed 0.996309119 seconds
Watching paths: /home/niwi/tmp/cljs-playground/src, /home/niwi/tmp/cljs-playground/assets
```

And raise unexpected exception when resulting script is executed:

```bash
$ node out/result.js
fs.js:640
  return binding.open(pathModule._makeLong(path), stringToFlags(flags), mode);
                 ^

Error: ENOENT: no such file or directory, open 'out/result/assets/_rxjs/rx.js'
    at Error (native)
    at Object.fs.openSync (fs.js:640:18)
    at Object.fs.readFileSync (fs.js:508:33)
    at Object.nodeGlobalRequire (/home/niwi/tmp/cljs-playground/out/result/goog/bootstrap/nodejs.js:87:26)
    at Object.cljs$core$load_file [as load_file] (/home/niwi/tmp/cljs-playground/out/result/cljs/core.js:332:13)
    at /home/niwi/tmp/cljs-playground/out/result/testproject/core.js:4:11
    at ContextifyScript.Script.runInThisContext (vm.js:25:33)
    at Object.exports.runInThisContext (vm.js:77:17)
    at nodeGlobalRequire (/home/niwi/tmp/cljs-playground/out/result/goog/bootstrap/nodejs.js:87:6)
    at global.CLOSURE_IMPORT_SCRIPT (/home/niwi/tmp/cljs-playground/out/result/goog/bootstrap/nodejs.js:75:3)
```

Seems like the **assets** subdirectory is stripped from the final copy destination but still
referenced with it on the source code (out/result/testproject/core.js):

```js
// Compiled by ClojureScript 1.9.474 {:target :nodejs}
goog.provide('testproject.core');
goog.require('cljs.core');
cljs.core.load_file("out/result/assets/_rxjs/rx.js");
cljs.core.enable_console_print_BANG_.call(null);
```
