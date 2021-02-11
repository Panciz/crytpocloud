#!/bin/bash


java -cp $(dirname "$0")/target/client-0.0.1-SNAPSHOT.jar:$(dirname "$0")/target/bcpkix-jdk15on.jar:$(dirname "$0")/target/bcprov-jdk15on.jar org.dpoletti.cryptocloud.client.CryptoCloudSimpleClient $1 $2 $3 $4 $5 $6