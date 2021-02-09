# Cryptocloud Client 	

Client application that allows the user to encrypt and upload and to download and to decrypt it.


To send file `testdir/sendf/test-image.jpg`

```
java -jar target/client-0.0.1-SNAPSHOT.jar PUT testdir/sendf/test-image.jpg dpoletti 127.0.0.1 9000
```

Retrieve  file `test-image.jpg` and store it in directory `testdir/recf/`

```
java -jar target/client-0.0.1-SNAPSHOT.jar GET testdir/recf/test-image.jpg dpoletti 127.0.0.1 9000
```