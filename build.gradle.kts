plugins {
    id("elucidator.root")
}

tasks.prepareKotlinBuildScriptModel {
    dependsOn(
        project(":elucidator").tasks.clean,
        project(":elucidator-generator").tasks.named("run"),
    )
}
