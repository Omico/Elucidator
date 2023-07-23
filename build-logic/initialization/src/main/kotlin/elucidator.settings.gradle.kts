pluginManagement {
    repositories {
        maven(url = "https://maven.omico.me")
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("gradm")
    id("elucidator.gradle-enterprise")
}

includeBuild("build-logic/project")
