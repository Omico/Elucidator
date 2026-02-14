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
            dependsOn(project.tasks.clean)
            if (gradle.parent == null) dependsOn(project(":elucidator-generator").tasks.named("run"))
        }
    }
    listOf(publish, publishToMavenLocal).forEach { task ->
        task {
            dependsOn(rootProject.tasks.spotlessApply, project.tasks.test)
        }
    }
}
