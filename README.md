# Swift Kotlin Sample

This is a small project for demonstrating binding Kotlin and Swift code in one project.
Current implementation of binding builded on [Readdle's swift-java-codegen project](https://github.com/readdle/swift-java-codegen)).

https://github.com/andriydruk/swift-kotlin-sample/blob/main/.github/workflows/mac.yml

### Mac ![mac ci](https://github.com/andriydruk/swift-kotlin-sample/workflows/macOS/badge.svg)

Enviroment:
* JDK 8+
* Xcode 11+

Clone project and run

```
./gradlew test
```

### Linux

Comming soon...

### Windows

Comming soon...

### Andorid

[@see Readdle swift-java-codegen project](https://github.com/readdle/swift-java-codegen)

### Additional command

Clean swift code:
```
./gradlew swiftPackageClean
```

Update swift packages:
```
./gradlew swiftPackageUpdate
```
