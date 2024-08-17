plugins {
  id("java-gradle-plugin")
  kotlin("jvm")
  kotlin("kapt")
  id("com.github.gmazzo.buildconfig")
}

dependencies {
  implementation(kotlin("gradle-plugin-api"))
  kapt("com.google.auto.service:auto-service:1.1.1")
  compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
  testCompileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
}

buildConfig {
  val project = project(":kotlin-ir-plugin")
  packageName(project.group.toString())
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${project.group}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${project.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${project.version}\"")
}

gradlePlugin {
  plugins {
    create("kotlinJooqChecker") {
      id = rootProject.extra["kotlin_plugin_id"] as String
      displayName = "Kotlin jOOQ Checker plugin"
      description = "Kotlin jOOQ Checker Plugin using IR and KAPT"
      implementationClass = "com.nicchongwb.plugins.KotlinJooqCheckerGradlePlugin"
    }
  }
}
