# kotlin-jooq-checker

# Usage

build.gradle
```groovy
plugins {
    id 'io.github.nicchongwb.ktjooqchecker' version '0.1.3'
}

kotlinJooqChecker {
  debugDumpIr = true
}
```

build.gradle.kts
```kotlin
plugins {
    id("io.github.nicchongwb.ktjooqchecker") version "0.1.3"
}

kotlinJooqChecker {
  debugDumpIr = true
}
```

| Plugin option | Value   | Description                                            |
|---------------|---------|--------------------------------------------------------|
| debugDumpIr   | boolean | Optional configuration to dump IR tree to build --info |



# Compatibility

| Kotlin Version | jOOQ Version | Plugin Version |
|----------------|--------------|----------------|
| 1.8.20         | 3.16+        | 0.1.x          |

Plugin version 0.X.X only supports K1 compilation. Future works will include K2 support, however, breaking changes will be expected.

## jOOQ code-generate Compatibility
If generated Kotlin classes from jOOQ codegen exist in the project's build source directory, there may be @PlainSQL present. The plugin will detect these annotations resulting in false positives. There are a few strategies to prevent this from happening.

1. Configure jOOQ codegen to generate Java classes instead, as this plugin only work with the Kotlin build source.
2. Reconfigure the generated Kotlin classes from jOOQ codegen to another project that doesn't apply this plugin.


# FAQ
What is jOOQ and potential security issues with it?
- jOOQ is a database-mapping library for Java and Kotlin(JVM). Although jOOQ provides Typesafe APIs, jOOQ also enables developers to use unsafe APIs also known as [Plain SQL](https://www.jooq.org/doc/latest/manual/sql-building/plain-sql/), which increases the attack surface of potential SQL injections.

Why not use a SAST solution to detect for PlainSQL?
- Depending on the SAST (Static Application Security Testing) compilation engine, they may not able to detect for PlainSQL usages due to the lack of rich semantics. jOOQ has a compiler plugin that is able to inspect the AST and detect PlainSQL usages, however this has its limited support.

Why not use native jOOQ Checker provided for my Kotlin projects?
- Native jOOQ Checker (compiler plugin) only works with Java source compilation due to the limited support of [error-prone](https://github.com/google/error-prone) and [checker-framework](https://github.com/typetools/checker-framework), which powers jOOQ Checker (Java).

What does this plugin do?
Kotlin jOOQ Checker will detect for PlainSQL usages during Kotlin source compilation. The checker is a Kotlin compiler plugin that utilises Kotlin Intermediate Representation (IR) and Kotlin Annotation Processor Tool (KAPT).

Why not use Kotlin Symbol Processing (KSP) instead of KAPT?
- KSP only processes annotations from declarations and not expressions. If a PlainSQL API is used, the `@PlainSQL` annotation doesn't explicitly appear in the source file. Apart from declarations, KAPT will be able to also process annotations of functions called in an expression.

# Credits
- Brian Norman (Bnorm) for his guide on writing a Kotlin compiler plugin.
- Amanda Hinchman-Dominguez (ahinchman1) for her articles on Kotlin compiler.
