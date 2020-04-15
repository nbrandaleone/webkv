FROM ubuntu:focal AS BASE

# Update OS and install build tools
RUN apt-get update
RUN apt-get install -y curl leiningen build-essential zlib1g-dev
WORKDIR /opt
RUN curl -sL https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.0.0/graalvm-ce-java11-linux-amd64-20.0.0.tar.gz \
  | tar -xzvf -

# Add Clojure project configuration file
ADD project.clj .
# Install software dependencies and create JAR release
RUN lein deps
ADD src src
RUN lein uberjar 

# Add native-image to Graal. It was recently separated out as a package.
RUN graalvm-ce-java11-20.0.0/bin/gu install native-image

# Use Graal to create native-image executable from JAR
RUN graalvm-ce-java11-20.0.0/bin/native-image \
  -H:EnableURLProtocols=http --report-unsupported-elements-at-runtime \
  --static --no-server --no-fallback --initialize-at-build-time \
  -cp target/webkv-0.1.0-SNAPSHOT-standalone.jar webkv.core

# Create release image
FROM alpine:3.11
COPY --from=BASE /opt/webkv.core /
EXPOSE 8080/tcp
CMD ["/webkv.core"]
