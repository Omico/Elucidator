rootProject.name = "elucidator-root"

pluginManagement {
    includeBuild("build-logic/initialization")
}

plugins {
    id("elucidator")
}

include(":elucidator")
include(":elucidator-generator")

if (System.getProperty("idea.active") == "true" && System.getProperty("o1.enabled") != "true") {
    includeBuild("kotlinpoet")
}
