#!/bin/bash
git checkout main
mvn javadoc:javadoc || exit 1

mkdir -p javadoc-temp/gmage-core/
mkdir -p javadoc-temp/gmage-android/
mkdir -p javadoc-temp/gmage-desktop/

cp -r gmage-core/target/site/apidocs/* javadoc-temp/gmage-core/
cp -r gmage-android/target/site/apidocs/* javadoc-temp/gmage-android/
cp -r gmage-desktop/target/site/apidocs/* javadoc-temp/gmage-desktop/

cp README.md README.temp.md

git checkout gh-pages

rm -r javadoc/
mv javadoc-temp/ javadoc/

rm -r README.md
mv README.temp.md README.md

git add README.md javadoc/
echo "Now commit me!"