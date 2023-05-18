plugins {
    id("elucidator.build-logic.root-project")
}

tasks.prepareKotlinBuildScriptModel {
    dependsOn(":elucidator:clean", ":elucidator-generator:run")
}
