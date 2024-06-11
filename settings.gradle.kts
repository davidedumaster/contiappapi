pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            credentials { username = "jp_m9ef3fjho6b0p34928m0a7c57o" }
        }
    }
}

rootProject.name = "bancontinentalapi"
include(":app")
include(":contiappapi")
