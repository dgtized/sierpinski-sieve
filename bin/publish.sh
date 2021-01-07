#!/bin/bash -e

function cleanup() {
    rm -rf resources/public/{.git,js} target
}

cleanup

clojure -m figwheel.main --build-once release

mkdir -p resources/public/js
cp target/public/js/sierpinsky_sieve.* resources/public/js

pushd resources/public
git init
git checkout -b gh-pages
git add index.html js/* css/*
git commit -m "Deploy to GitHub Pages"
git push --force --quiet "git@github.com:dgtized/sierpinski-sieve.git" gh-pages:gh-pages
popd

cleanup
