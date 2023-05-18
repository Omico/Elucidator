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
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(libs.elucidator)
    implementation(libs.kotlinpoet)
}

tasks.run<JavaExec> {
    args = listOf(projectDir.resolveSibling("elucidator/build/generated/kotlin").absolutePath)
}
