import me.omico.age.spotless.configureSpotless
import me.omico.age.spotless.intelliJIDEARunConfiguration
import me.omico.age.spotless.kotlin
import me.omico.age.spotless.kotlinGradle

plugins {
    id("com.diffplug.spotless")
    id("common.build-logic.root-project.base")
    id("me.omico.age.spotless")
}

allprojects {
    configureSpotless {
        freshmark {
            target("**/*.md")
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }
        intelliJIDEARunConfiguration()
        kotlin(
            excludeTargets = listOf("kotlinpoet/**"),
            licenseHeaderFile = rootProject.file("spotless/copyright.kt"),
            licenseHeaderConfig = {
                updateYearWithLatest(true)
                yearSeparator("-")
            },
        )
        kotlinGradle(
            additionalExcludeTargets = setOf("kotlinpoet/**"),
        )
    }
}

subprojects {
    rootProject.tasks {
        spotlessApply { dependsOn(this@subprojects.tasks.spotlessApply) }
        spotlessCheck { dependsOn(this@subprojects.tasks.spotlessCheck) }
    }
}
