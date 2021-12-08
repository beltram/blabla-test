plugins {
    kotlin("jvm") version "1.6.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"
val junitVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core:3.21.0")
}

tasks.withType<Test>().configureEach { useJUnitPlatform() }