// swift-tools-version:5.0
import Foundation
import PackageDescription

let packageName = "SampleAppCore"

let package = Package(
    name: packageName,
    products: [
        .library(name: packageName, type: .dynamic, targets: [packageName])
    ],
    dependencies: [
        .package(url: "https://github.com/readdle/java_swift.git", .branch("dev/kotlin-support")),
        .package(url: "https://github.com/readdle/swift-java.git", .upToNextMinor(from: "0.2.2")),
        .package(url: "https://github.com/readdle/swift-java-coder.git", .branch("dev/kotlin-support")),
        .package(url: "https://github.com/readdle/swift-anycodable.git", .upToNextMinor(from: "1.0.3")),
    ],
    targets: [
        .target(name: packageName, dependencies: ["AnyCodable", "Java", "java_swift", "JavaCoder"])
    ]
)
