import me.omico.gradm.addDeclaredRepositories

addDeclaredRepositories()

plugins {
    id("elucidator.gradm")
    id("elucidator.gradle-enterprise")
}

includeBuild("build-logic/project")
