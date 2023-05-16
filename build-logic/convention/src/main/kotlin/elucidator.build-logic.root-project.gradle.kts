plugins {
    id("gradm")
    id("common.build-logic.root-project.base")
    id("common.build-logic.git.hooks")
    id("elucidator.build-logic.spotless")
}

val wrapper: Wrapper by tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}
