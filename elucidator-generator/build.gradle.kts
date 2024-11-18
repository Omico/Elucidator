plugins {
    application
    kotlin("jvm")
}

application {
    applicationName = "DslGenerator"
    mainClass.set("me.omico.elucidator.DslGenerator")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("reflect"))
    implementation(delusion.kotlin.compiler.embeddable)
    implementation(libs.elucidator)
    implementation(libs.kotlinpoet)
}

tasks.run<JavaExec> {
    dependsOn(":spotlessApply")
    args = listOf(
        projectDir.resolveSibling("kotlinpoet/kotlinpoet/src/jvmMain").absolutePath,
        projectDir.resolveSibling("elucidator/src/generated/kotlin").absolutePath,
    )
}
