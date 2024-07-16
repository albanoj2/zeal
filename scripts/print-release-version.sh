#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

cd ${PROJECT_ROOT}

mvn help:evaluate -Dexpression=project.version -q -DforceStdout | cut -d '-' -f 1