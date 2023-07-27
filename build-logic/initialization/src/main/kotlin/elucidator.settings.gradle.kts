import me.omico.gradm.addDeclaredRepositories

addDeclaredRepositories()

plugins {
    id("gradm")
    id("elucidator.gradle-enterprise")
}

includeBuild("build-logic/project")
