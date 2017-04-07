# sierpinski-sieve

Explore Sierpinski triangles in Clojurescript

## Overview

https://dgtized.github.io/sierpinski-sieve

Mostly an exploration of fast pixel manipulation from Clojurescript
of the HTML5 Canvas API.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

See also https://github.com/binaryage/cljs-devtools

## License

Copyright (C) 2017 Charles L.G. Comstock

Distributed under the MIT Public License, see LICENSE file
