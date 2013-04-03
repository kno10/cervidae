#!/bin/sh
mvn compile package install || exit 1
mvn -pl microbenchmark-launcher appassembler:assemble || exit 1
sh microbenchmark-launcher/target/appassembler/bin/benchmark-sort "$@"
