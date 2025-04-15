plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.insert-koin:koin-core:4.0.2")
    implementation ("com.opencsv:opencsv:5.6")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
}

tasks.test {
    useJUnitPlatform()
}