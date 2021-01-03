#!/bin/bash -e

rm -rf resources/public/.git
clojure -m figwheel.main --build-once release

pushd resources/public
git init
git add .
git commit -m "Deploy to GitHub Pages"
git push --force --quiet "git@github.com:dgtized/sierpinski-sieve.git" master:gh-pages
popd
