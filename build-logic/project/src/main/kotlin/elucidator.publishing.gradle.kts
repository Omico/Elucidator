plugins {
    id("me.omico.consensus.publishing")
}

consensus {
    publishing {
        when {
            isCi -> publishToNexusRepository()
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
                name = providers.gradleProperty("POM_NAME")
                description = providers.gradleProperty("POM_DESCRIPTION")
                url = "https://github.com/Omico/Elucidator"
                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "Omico"
                        name = "Omico"
                    }
                }
                scm {
                    url = "https://github.com/Omico/Elucidator"
                    connection = "scm:git:https://github.com/Omico/Elucidator.git"
                    developerConnection = "scm:git:https://github.com/Omico/Elucidator.git"
                }
            }
        }
    }
}

extensions.findByType<JavaPluginExtension>()?.apply {
    withSourcesJar()
    withJavadocJar()
}
