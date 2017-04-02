#!/bin/bash -e

rm -rf resources/public/.git
lein do clean, cljsbuild once min

pushd resources/public
git init
git add .
git commit -m "Deploy to GitHub Pages"
git push --force --quiet "git@github.com:dgtized/sierpinski-sieve.git" master:gh-pages
popd
