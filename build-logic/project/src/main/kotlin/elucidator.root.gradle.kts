plugins {
    id("me.omico.consensus.root")
    id("elucidator.gradm")
    id("elucidator.root.git")
    id("elucidator.root.spotless")
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}
