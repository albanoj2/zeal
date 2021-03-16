#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

MODULES=$(find . -maxdepth 1 -type d | grep "zeal-")

cd $DIR/..

mkdir -p output/reports


for MODULE in $MODULES
do
    JACOCO_DIR=$MODULE/target/site/jacoco/
    DEST_DIR=output/reports/$MODULE

    if [ -d $JACOCO_DIR ]
    then
        mkdir -p $DEST_DIR
        cp -r $JACOCO_DIR $DEST_DIR
        echo "Copied $JACOCO_DIR to $DEST_DIR."
    fi
done