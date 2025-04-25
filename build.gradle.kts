plugins {
    kotlin("jvm") version "2.0.21"
    id("jacoco")  // Add this line to apply the Jacoco plugin

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.insert-koin:koin-core:4.0.2")
    implementation ("com.opencsv:opencsv:5.6")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.4.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testImplementation("io.mockk:mockk:1.14.0")

}

tasks.test {
    useJUnitPlatform()
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {

                include(
                    "**/logic/**",
                    "**/presentation/**"
                )
                exclude(
                    "**/presentation/io",
                    "**/logic/repository",
                )
            }
        })
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "PACKAGE"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.0".toBigDecimal()
            }
        }
    }
}
