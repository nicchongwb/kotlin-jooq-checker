pluginManagement {
  val pluginVersion: String by settings
  plugins {
    id("io.github.nicchongwb.ktjooqchecker") version pluginVersion
    id("nu.studer.jooq") version "8.0"
  }
}

rootProject.name = "kotlin-jooq-checker-integration"

//includeBuild("..")
include(":kotlin-v1-8-20")
