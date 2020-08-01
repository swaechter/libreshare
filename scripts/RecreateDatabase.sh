#!/bin/bash

set -e

SCRIPT_PATH=$(dirname "$0")
pushd "$SCRIPT_PATH"

./DeleteDatabase.sh
./CreateDatabase.sh

popd
