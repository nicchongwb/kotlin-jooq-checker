import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  extra["kotlin_plugin_id"] = "com.nicchongwb.ktjooqchecker"
}

plugins {
  kotlin("jvm") version "1.8.20" apply false
  id("org.jetbrains.dokka") version "1.9.0" apply false
  id("com.gradle.plugin-publish") version "1.2.1" apply false
  id("com.github.gmazzo.buildconfig") version "3.1.0" apply false
  id("nu.studer.jooq") version "8.0" apply false
}

allprojects {
  group = "com.nicchongwb.ktjooqchecker"
  version = "0.1.0"

  tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }
}

subprojects {
  repositories {
    mavenCentral()
  }
}
