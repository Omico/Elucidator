plugins {
    kotlin("jvm")
    id("elucidator.publishing")
}

kotlin {
    jvmToolchain(11)
    explicitApi()
    sourceSets.main {
        kotlin.srcDir("src/generated/kotlin")
    }
}

dependencies {
    compileOnly(libs.kotlinpoet)
}

dependencies {
    testImplementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinpoet)
}

tasks {
    listOf(compileKotlin, sourcesJar).forEach { task ->
        task {
            dependsOn(":elucidator:clean", ":elucidator-generator:run")
        }
    }
    listOf(publish, publishToMavenLocal).forEach { task ->
        task {
            dependsOn(":spotlessApply", ":elucidator:test")
        }
    }
}
