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
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}