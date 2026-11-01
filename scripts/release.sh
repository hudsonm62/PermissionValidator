#!/bin/sh
set -eu

./gradlew buildAndCollect publishMods --stacktrace
