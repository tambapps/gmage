#!/bin/bash
git checkout main || exit 1 > /dev/null
mvn javadoc:javadoc || exit 1

mkdir -p javadoc-temp/gmage-core/
mkdir -p javadoc-temp/gmage-android/
mkdir -p javadoc-temp/gmage-desktop/

cp -r gmage-core/target/site/apidocs/* javadoc-temp/gmage-core/
cp -r gmage-android/target/site/apidocs/* javadoc-temp/gmage-android/
cp -r gmage-desktop/target/site/apidocs/* javadoc-temp/gmage-desktop/

cp README.md README.temp.md

git checkout gh-pages || exit 1 > /dev/null

rm -r javadoc/
mv javadoc-temp/ javadoc/

rm -r README.md
mv README.temp.md README.md

# don't know why it doesn't delete gmage directories
rm -r -f gmage-*

git add README.md javadoc/
echo "Now commit me!"