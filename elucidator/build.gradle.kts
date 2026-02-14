plugins {
    id("elucidator.kotlin-jvm")
    id("elucidator.publishing")
}

kotlin {
    jvmToolchain(8)
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
    testImplementation(kotlin("test-junit"))
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
