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
        maven {
            url = uri("https://jitpack.io")
            metadataSources {
                mavenPom()
            }

        }
        google()
        mavenCentral()

    }
}

rootProject.name = "bancontinentalapi"
include(":app")
include(":contiappapi")
