# webkv

A Clojure web-based API server, which acts as a key-value store.
It creates a small file with the same name as the key. The value
is stored inside the file.

The files are written to the "/tmp" directory.

The API server accepts HTTP PUT and GET for storing
and retrieving values.

There is an included Dockerfile, meant to build this code using GraalVM.
The container is hosted on DockerHub: nbrand/webkv:0.1.

This program is meant as a learning example only.
Use redis for a production grade Key-Value store.

NOTE: This program was inspired and modified from the following sources:
* [YouTube](https://www.youtube.com/watch?v=topKYJgv6qA)
* [slides](https://www.janstepien.com/native-clojure-with-graalvm/)
* [Blog](https://www.innoq.com/en/blog/native-clojure-and-graalvm/)

## Usage

### Testing

``` sh
$ curl localhost:8080/kv/city -i -X PUT -d val=Boston
HTTP/1.1 201 Created
Content-Type: application/octet-stream
Content-Length: 7
Server: http-kit
Date: Tue, 14 Apr 2020 20:49:07 GMT

Boston

$ curl localhost:8080/kv/city -i
HTTP/1.1 200 OK
Content-Type: application/octet-stream
Content-Length: 7
Server: http-kit
Date: Tue, 14 Apr 2020 20:51:50 GMT

Boston
```

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
