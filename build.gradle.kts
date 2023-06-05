plugins {
    id("elucidator.root")
}

tasks.prepareKotlinBuildScriptModel {
    dependsOn(":elucidator:clean", ":elucidator-generator:run")
}
