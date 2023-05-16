plugins {
    id("elucidator.build-logic.root-project")
}

tasks.prepareKotlinBuildScriptModel {
    dependsOn(":elucidator-generator:run")
}
