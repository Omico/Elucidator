import me.omico.consensus.dsl.isGitDirectoryExists
import me.omico.consensus.dsl.requireRootProject
import me.omico.gradm.Versions

plugins {
    id("me.omico.consensus.git")
}

requireRootProject()

consensus {
    git {
        // TODO Replace with `isCi` property after Consensus 0.7.0 is released.
        if (environmentVariables.getOrDefault("CI", false)) return@git
        if (!isGitDirectoryExists) return@git
        hooks {
            preCommit {
                appendLine("#!/bin/sh")
                appendLine()
                if (file("gradlew").exists()) {
                    appendLine("# Give gradlew execute permission")
                    appendLine("git ls-files \"*gradlew\" | xargs git update-index --add --chmod=+x")
                    appendLine()
                }
            }
        }
        operations {
            checkoutTag(Versions.kotlinpoet, workingDir = file("kotlinpoet"))
        }
    }
}
