#!/bin/bash
mvn javadoc:javadoc || exit 1

mkdir -p javadoc/gmage-core/
mkdir -p javadoc/gmage-android/
mkdir -p javadoc/gmage-desktop/

cp -r gmage-core/target/apidocs/* javadoc/gmage-core/
cp -r gmage-android/target/apidocs/* javadoc/gmage-android/
cp -r gmage-desktop/target/apidocs/* javadoc/gmage-desktop/

echo "Java docs generated in $(pwd)/javadoc"