import me.omico.consensus.dsl.requireRootProject

plugins {
    id("gradm")
    id("elucidator.root.git")
    id("elucidator.root.spotless")
}

requireRootProject()

val wrapper: Wrapper by tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}
