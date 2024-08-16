# kotlin-jooq-checker

Why jOOQ PlainSQLChecker for Kotlin?
- jOOQ is a database-mapping library for Java and Kotlin(JVM). Although jOOQ provides Typesafe APIs, jOOQ also enables developers to use unsafe APIs also known as [Plain SQL](https://www.jooq.org/doc/latest/manual/sql-building/plain-sql/), which increases the attack surface of potential SQL injections.
- Depending on the SAST (Static Application Security Testing) compilation engine, they may not able to detect for PlainSQL usages due to the lack of rich semantics. jOOQ has a compiler plugin that is able to inspect the AST and detect PlainSQL usages, however this has its limited support.  
- Native jOOQ Checker (compiler plugin) only works with Java source compilation due to the limited support of [error-prone](https://github.com/google/error-prone) and [checker-framework](https://github.com/typetools/checker-framework), which powers jOOQ Checker (Java).

Kotlin jOOQ Checker will detect for PlainSQL usages during Kotlin source compilation. The checker is a Kotlin compiler plugin that utilises Kotlin Intermediate Representation (IR) and Kotlin Annotation Processor Tool (KAPT).

Why not use Kotlin Symbol Processing (KSP) instead of KAPT?
- KSP only processes annotations from declarations and not expressions. If a PlainSQL API is used, the `@PlainSQL` annotation doesn't explicitly appear in the source file. Apart from declarations, KAPT will be able to also process annotations of functions called in an expression.

# Usage


# Support
