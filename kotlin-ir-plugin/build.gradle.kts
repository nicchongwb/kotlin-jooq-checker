import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
  kotlin("jvm")
  kotlin("kapt")
  id("com.github.gmazzo.buildconfig")
  id("nu.studer.jooq")
  id("java")
}

dependencies {
  /*
   Refer to https://github.com/tschuchortdev/kotlin-compile-testing?tab=readme-ov-file#compatible-compiler-versions
   for kotlin-compiler-embeddable version for compileOnly & testImplementation configuration
   */
  compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.24")
  testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.24")

  kapt("com.google.auto.service:auto-service:1.1.1")
  compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
  testCompileOnly("com.google.auto.service:auto-service-annotations:1.1.1")


  /*
  * Configure jOOQ Dependency
  * - this imported dependency is only compiler to check against @Allow.PlainSQL qualified name
  */
  implementation("org.jooq:jooq:3.17.0")

  testImplementation(kotlin("test-junit"))
  testImplementation("dev.zacsweers.kctfork:core:0.4.0")
  testImplementation("org.jooq:jooq:3.17.0")
  testImplementation("com.h2database:h2:2.3.232")
}

buildConfig {
  packageName(group.toString())
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
}
