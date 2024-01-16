pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("https://jitpack.io"))
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(uri("https://jitpack.io"))
        flatDir{ dirs("app/libs") }
    }
}

rootProject.name = "Password Store"
include(":app")
