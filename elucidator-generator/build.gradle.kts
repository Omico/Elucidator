plugins {
    application
    kotlin("jvm")
}

application {
    applicationName = "DslGenerator"
    mainClass.set("me.omico.elucidator.DslGenerator")
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(kotlin("compiler"))
    implementation(libs.dokka.analysis)
    implementation(libs.dokka.core)
    implementation(libs.elucidator)
    implementation(libs.kotlinpoet)
}

tasks.run<JavaExec> {
    dependsOn(":spotlessApply")
    args = listOf(
        projectDir.resolveSibling("kotlinpoet").absolutePath,
        projectDir.resolveSibling("elucidator/build/generated/kotlin").absolutePath,
    )
}
