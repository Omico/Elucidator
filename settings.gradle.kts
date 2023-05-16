@file:Suppress("UnstableApiUsage")

rootProject.name = rootProject.projectDir.name

pluginManagement {
    includeBuild("build-logic/gradm")
    repositories {
        mavenCentral()
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version embeddedKotlinVersion
    }
}

plugins {
    id("gradm")
    `gradle-enterprise`
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(!gradle.startParameter.isOffline)
    }
}

includeBuild("build-logic")

include(":elucidator")
include(":elucidator-generator")
