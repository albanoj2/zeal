#!/bin/bash

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)
PROJECT_ROOT=$SCRIPT_DIR/..

cd ${PROJECT_ROOT}

mvn -X release:prepare \
    release:perform \
	  $@
