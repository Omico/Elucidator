import me.omico.consensus.dsl.requireRootProject
import me.omico.consensus.spotless.ConsensusSpotlessTokens

plugins {
    id("me.omico.consensus.spotless")
}

requireRootProject()

consensus {
    spotless {
        rootProject {
            freshmark()
            gradleProperties()
            intelliJIDEARunConfiguration()
        }
        allprojects {
            kotlin(
                excludeTargets = setOf(
                    "kotlinpoet/**",
                    "src/generated/kotlin/**",
                ),
                licenseHeaderFile = rootProject.file("spotless/copyright.kt"),
            )
            kotlinGradle(
                excludeTargets = ConsensusSpotlessTokens.KotlinGradle.excludeTargets + setOf("kotlinpoet/**"),
            )
        }
    }
}

subprojects {
    rootProject.tasks {
        spotlessApply { dependsOn(this@subprojects.tasks.spotlessApply) }
        spotlessCheck { dependsOn(this@subprojects.tasks.spotlessCheck) }
    }
}
