#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

cd "$PROJECT_ROOT" || exit 1

mvn -B clean install verify -Psonar "$@"
