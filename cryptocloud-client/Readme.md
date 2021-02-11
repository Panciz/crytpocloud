# Cryptocloud Client 	

Client application that allows the user to encrypt and upload and to download and to decrypt it.

To the the client run the server on port 9000

# No encrypt version


To send file `testdir/sendf/test-image.jpg` 

```
java -jar target/client-0.0.1-SNAPSHOT.jar PUT testdir/sendf/test-image.jpg dpoletti 127.0.0.1 9000
```

Retrieve  file `test-image.jpg` and store it in directory `testdir/recf/` 

```
java -jar target/client-0.0.1-SNAPSHOT.jar GET testdir/recf/test-image.jpg dpoletti 127.0.0.1 9000
```

check the result with 

```
md5sum -c testdir/recf/test-image.md5
rm testdir/recf/*
```

#  encrypt version


To send file `testdir/sendf/test-image.jpg`, encryption key will be creted if not present

```
java -cp target/client-0.0.1-SNAPSHOT.jar:target/bcpkix-jdk15on.jar:target/bcprov-jdk15on.jar org.dpoletti.cryptocloud.client.CryptoCloudSimpleClient PUT testdir/sendf/test-image.jpg dpoletti 127.0.0.1 9000 testdir/enc.key
```

Retrieve  file `test-image.jpg` and store it in directory `testdir/recf/` 

```
java -cp target/client-0.0.1-SNAPSHOT.jar:target/bcpkix-jdk15on.jar:target/bcprov-jdk15on.jar org.dpoletti.cryptocloud.client.CryptoCloudSimpleClient GET testdir/recf/test-image.jpg dpoletti 127.0.0.1 9000 testdir/enc.key
```

check the result with 

```
md5sum -c testdir/recf/test-image.md5
rm testdir/recf/*
rm testdir/enc.key
```




