plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.qudus.squad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    implementation("io.insert-koin:koin-core:4.0.4")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:6.0.0.M3")
    testImplementation("io.kotest:kotest-assertions-core:6.0.0.M3")

    // parameterized test
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")

    // mockk
    testImplementation("io.mockk:mockk:1.14.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}