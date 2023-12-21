import me.omico.gradm.Versions

plugins {
    id("me.omico.consensus.git")
}

consensus {
    git {
        if (isCi) return@git
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
