rootProject.name = "elucidator-root"

pluginManagement {
    includeBuild("build-logic/gradm")
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
