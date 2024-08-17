buildscript {
  extra["kotlin_plugin_id"] = "com.nicchongwb.plugins.kotlin-jooq-checker"
}

plugins {
  kotlin("jvm") version "1.9.0" apply false
  id("org.jetbrains.dokka") version "1.9.0" apply false
  id("com.gradle.plugin-publish") version "1.2.1" apply false
  id("com.github.gmazzo.buildconfig") version "5.3.5" apply false
  id("nu.studer.jooq") version "8.0" apply false
}

allprojects {
  group = "com.nicchongwb.plugins"
  version = "1.0.0-SNAPSHOT"
}

subprojects {
  repositories {
    mavenCentral()
  }
}
