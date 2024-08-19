import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("kapt")
  id("com.github.gmazzo.buildconfig")
  id("nu.studer.jooq")
  id("java")

//  signing
  `maven-publish`
}

dependencies {
  /*
   Refer to https://github.com/tschuchortdev/kotlin-compile-testing?tab=readme-ov-file#compatible-compiler-versions
   for latest kotlin-compiler-embeddable version for compileOnly & testImplementation configuration
   */
  compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
  testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
  testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")
  testImplementation(kotlin("test-junit"))


  kapt("com.google.auto.service:auto-service:1.0.1")
  compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
  testCompileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
  /*
  * Configure jOOQ Dependency
  * - this imported dependency is only compiler to check against @Allow.PlainSQL qualified name
  */
  implementation("org.jooq:jooq:3.17.0")
  testImplementation("org.jooq:jooq:3.17.0")
  testImplementation("com.h2database:h2:2.3.232")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.freeCompilerArgs += listOf(
    "-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi",
  )
}

buildConfig {
  packageName(group.toString())
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
}

tasks.register("sourcesJar", Jar::class) {
  group = "build"
  description = "Assembles Kotlin sources"

  archiveClassifier.set("sources")
  from(sourceSets.main.get().allSource)
  dependsOn(tasks.classes)
}

publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
      artifact(tasks["sourcesJar"])

      pom {
        name.set(project.name)
        description.set("Kotlin compiler plugin to perform API validation for jOOQ in Kotlin")
        url.set("https://github.com/nicchongwb/kotlin-jooq-checker")

        licenses {
          license {
            name.set("Apache License 2.0")
            url.set("https://github.com/nicchongwb/kotlin-jooq-checker/blob/master/LICENSE.txt")
          }
        }
        scm {
          url.set("https://github.com/nicchongwb/kotlin-jooq-checker")
          connection.set("scm:git:git://github.com/nicchongwb/kotlin-jooq-checker.git")
        }
        developers {
          developer {
            name.set("Nicholas Chong")
            url.set("https://github.com/nicchongwb")
          }
        }
      }

      repositories {
        maven {
          name = "test"
          url = uri(rootProject.layout.buildDirectory.dir("localMaven"))
        }

        maven {
          name = "OSSRH"
          url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
          credentials {
            username = System.getenv("MAVEN_USERNAME")
            password = System.getenv("MAVEN_PASSWORD")
          }
        }

        maven {
          name = "GitHubPackages"
          url = uri("https://maven.pkg.github.com/nicchongwb/kotlin-jooq-checker")
          credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
          }
        }
      }
    }
  }
}
