#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

cd $DIR/..

mkdir -p output/artifacts

cp -r */target/*.jar output/artifacts