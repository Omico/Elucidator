import me.omico.consensus.dsl.by

plugins {
    id("me.omico.consensus.publishing")
}

consensus {
    publishing {
        when {
            environmentVariables.getOrDefault("CI", false) -> publishToNexusRepository()
            else -> publishToLocalRepository("MAVEN_OMICO_LOCAL_URI")
        }
        signing {
            if (isSnapshot) return@signing
            useGpgCmd()
            sign(publications)
        }
        createMavenPublication {
            from(components["java"])
            pom {
                name by gradleProperty("POM_NAME")
                description by gradleProperty("POM_DESCRIPTION")
                url by "https://github.com/Omico/Elucidator"
                licenses {
                    license {
                        name by "The Apache Software License, Version 2.0"
                        url by "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id by "Omico"
                        name by "Omico"
                    }
                }
                scm {
                    url by "https://github.com/Omico/Elucidator"
                    connection by "scm:git:https://github.com/Omico/Elucidator.git"
                    developerConnection by "scm:git:https://github.com/Omico/Elucidator.git"
                }
            }
        }
    }
}

extensions.findByType<JavaPluginExtension>()?.apply {
    withSourcesJar()
    withJavadocJar()
}
