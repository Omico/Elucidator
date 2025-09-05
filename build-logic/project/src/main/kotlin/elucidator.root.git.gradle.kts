import me.omico.consensus.api.dsl.requireRootProject
import me.omico.gradm.Versions

plugins {
    id("me.omico.consensus.git")
}

requireRootProject()

consensus {
    git {
        hooks {
            preCommit {
                correctGradleWrapperScriptPermissions()
                checkGitAttributes()
            }
        }
        operations {
            exec("git", "fetch", "origin", "--tags", workingDir = file("kotlinpoet"))
            checkoutTag(Versions.kotlinpoet, workingDir = file("kotlinpoet"))
        }
    }
}
