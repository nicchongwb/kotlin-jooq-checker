plugins {
  kotlin("jvm") version "1.8.20"
  id("io.github.nicchongwb.ktjooqchecker")
  id("nu.studer.jooq") version "8.0"
  id("java")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jooq:jooq:3.17.0")
  implementation("com.h2database:h2:2.3.232")
}

kotlinJooqChecker {
  debugDumpIr = true
}
