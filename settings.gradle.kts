pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("(com\\.(android|google)|androidx?)(\\..*)?")
      }
    }
    mavenCentral()
  }
  includeBuild("build-logic")
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google {
      content {
        includeGroupByRegex("(com\\.(android|google)|androidx?)(\\..*)?")
      }
    }
    mavenLocal {
      content {
        includeGroup("im.molly")
        includeGroup("org.signal")
      }
    }
    maven {
      url = uri("https://dl.cloudsmith.io/public/mollyim/ringrtc/maven/")
      content {
        includeModule("im.molly", "ringrtc-android")
      }
    }
    maven {
      url = uri("https://dl.cloudsmith.io/public/mollyim/libsignal/maven/")
      content {
        includeModule("im.molly", "libsignal-client")
        includeModule("im.molly", "libsignal-android")
      }
    }
    maven {
      url = uri("https://raw.githubusercontent.com/signalapp/maven/master/sqlcipher/release/")
      content {
        includeModule("org.signal", "sqlcipher-android")
      }
    }
    maven {
      url = uri("https://raw.githubusercontent.com/signalapp/maven/master/aesgcmprovider/release/")
      content {
        includeModule("org.signal", "aesgcmprovider")
      }
    }
    mavenCentral()
  }
  versionCatalogs {
    // Keep the default libs catalog from gradle/libs.versions.toml and restore
    // the dependency aliases lost during the baseline-profile merge.
    named("libs") {
      library("dnsjava", "dnsjava", "dnsjava").version("3.6.4")
      library("kotlinx-collections-immutable", "org.jetbrains.kotlinx", "kotlinx-collections-immutable").version("0.4.0")
      library("arrow-core", "io.arrow-kt", "arrow-core").version("2.2.2.1")
      library("androidx-media3-exoplayer", "androidx.media3", "media3-exoplayer").version("1.9.1")
      library("androidx-media3-session", "androidx.media3", "media3-session").version("1.9.1")
      library("androidx-media3-ui", "androidx.media3", "media3-ui").version("1.9.1")
      bundle("media3", listOf("androidx-media3-exoplayer", "androidx-media3-session", "androidx-media3-ui"))
    }
    create("testLibs") {
      from(files("gradle/test-libs.versions.toml"))
    }
    create("lintLibs") {
      from(files("gradle/lint-libs.versions.toml"))
    }
  }
}

// To build libsignal from source, set the libsignalClientPath property in gradle.properties.
val libsignalClientPath = if (extra.has("libsignalClientPath")) extra.get("libsignalClientPath") else null
if (libsignalClientPath is String) {
  includeBuild(rootDir.resolve(libsignalClientPath + "/java")) {
    name = "libsignal-client"
    dependencySubstitution {
      substitute(module("im.molly:libsignal-client")).using(project(":client"))
      substitute(module("im.molly:libsignal-android")).using(project(":android"))
    }
  }
}

// Main app
include(":app")

// Baseline profile generator
include(":baseline-profile")

// Core modules
include(":core:util")
include(":core:util-jvm")
include(":core:models")
include(":core:models-jvm")
include(":core:network")
include(":core:ui")
include(":core:serialization")

// FOSS GMS modules
include(":core-gms:base")
include(":core-gms:cloud-messaging")
include(":core-gms:safeparcel")
include(":core-gms:safeparcel-processor")
include(":core-gms:tasks")

// Lib modules
include(":lib:libsignal-service")
include(":lib:netcipher")
include(":lib:network")
include(":lib:glide")
include(":lib:photoview")
include(":lib:sticky-header-grid")
include(":lib:paging")
include(":lib:device-transfer")
include(":lib:donations")
include(":lib:contacts")
include(":lib:qr")
include(":lib:video")
include(":lib:image-editor")
include(":lib:debuglogs-viewer")
include(":lib:blurhash")
include(":lib:apng")
include(":lib:archive")

// Feature modules
include(":feature:registration")
include(":feature:camera")
include(":feature:media-send")

// Testing/Lint modules
include(":lintchecks")
include(":fast-lint")

rootProject.name = "Ghostly"
