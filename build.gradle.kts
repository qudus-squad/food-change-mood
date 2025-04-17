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
    implementation("com.opencsv:opencsv:5.7.1")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.7.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}