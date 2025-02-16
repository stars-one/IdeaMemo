pluginManagement {
    repositories {
        maven("https://www.jitpack.io/")
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://jitpack.io")
        google()
        jcenter()
        mavenCentral()
    }
}

rootProject.name = "Note"
include(":app")
