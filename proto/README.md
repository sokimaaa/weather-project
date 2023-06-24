# Proto Module

Proto module contains protofiles for further using. 

Proto module should be built firstly, because it generates 
java classes that will be used in other modules. 

## Requirements

- Java 17+
- Apache Maven 3.8.6+
- (optional) Protocol Buffers compiler (libprotoc 23.1+)

## Build Project via Maven

To build Java classes from proto files using Maven,
you can just use following mvn command:

```
mvn clean compile
```

> Take into account that you should be in ./proto directory before execute.

> Take care that `protobuf-maven-plugin` works on your os.

## Build Project via Protoc

To build Java classes from proto files using protoc, you can use the following command-line query:

```
protoc --java_out=./target ./src/main/proto/../*.proto
```
