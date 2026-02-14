plugins {
    application
    id("elucidator.kotlin-jvm")
}

application {
    applicationName = "DslGenerator"
    mainClass = "me.omico.elucidator.DslGenerator"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(embeddedKotlin("reflect"))
}

dependencies {
    implementation(delusion.kotlin.compiler)
    implementation(libs.elucidator)
    implementation(libs.kotlinpoet)
}

tasks.run<JavaExec> {
    dependsOn(":spotlessApply")
    args =
        listOf(
            projectDir.resolveSibling("kotlinpoet/kotlinpoet/src/jvmMain").absolutePath,
            projectDir.resolveSibling("elucidator/src/generated/kotlin").absolutePath,
        )
}
