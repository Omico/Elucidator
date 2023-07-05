rootProject.name = "elucidator-build-logic"

pluginManagement {
    includeBuild("gradm")
}

plugins {
    id("gradm")
}

include(":convention")
