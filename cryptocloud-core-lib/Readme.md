# Cryptocloud Core Lib

This project contains the core of the server application.
It contains the service that listen for the incoming files.

It can be run as standalone application or included as a library.


## To test server locally

To save file `file.bin` as `user` `fileName`


```
java -jar original-cryptocloud-core-0.0.1-SNAPSHOT.jar  destDir
```

open  terminal 1

```
> mkfifo /tmp/fifo
> nc -v -n <>/tmp/fifo 127.0.0.1 9000 &
> ncpid=$!
> echo user@fileName > /tmp/fifo
> cat file.bin > /tmp/fifo
> kill -9 $ncpid
```


