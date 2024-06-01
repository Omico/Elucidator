import me.omico.gradm.addDeclaredRepositories

addDeclaredRepositories()

plugins {
    id("elucidator.develocity")
    id("elucidator.gradm")
}

includeBuild("build-logic/project")
