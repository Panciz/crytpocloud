#!/bin/bash

cd "$(dirname "$0")"
echo $PWD

mvn clean package -Pstandalone


echo "Cleaning...  "
rm -v testdir/recf/test-image.jpg || true
rm -v testdir/enc.key || true

echo "Sending test no encrypt"
java -jar target/client-0.0.1-SNAPSHOT.jar PUT testdir/sendf/test-image.jpg user 127.0.0.1 9000

echo "Retrive test no encrypt"
java -jar target/client-0.0.1-SNAPSHOT.jar GET testdir/recf/test-image.jpg user 127.0.0.1 9000


echo "Cheching no encrypt result"
md5sum -c testdir/recf/test-image.md5

echo "Cleaning...  "
rm -v testdir/recf/test-image.jpg || true

echo "Sending test  encrypt"
java -cp target/client-0.0.1-SNAPSHOT.jar:target/bcpkix-jdk15on.jar:target/bcprov-jdk15on.jar org.dpoletti.cryptocloud.client.CryptoCloudSimpleClient PUT testdir/sendf/test-image.jpg user 127.0.0.1 9000 testdir/enc.key

echo "Retrive test  encrypt"
java -cp target/client-0.0.1-SNAPSHOT.jar:target/bcpkix-jdk15on.jar:target/bcprov-jdk15on.jar org.dpoletti.cryptocloud.client.CryptoCloudSimpleClient GET testdir/recf/test-image.jpg user 127.0.0.1 9000 testdir/enc.key

echo "Cheching  encrypt result"
md5sum -c testdir/recf/test-image.md5

echo "Cleaning...  "
rm -v testdir/recf/test-image.jpg || true
rm -v testdir/enc.key || true