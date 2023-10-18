plugins {
    kotlin("jvm") version "1.9.0"

    id("maven-publish")
}

group = "com.dietrich"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")

    testImplementation(kotlin("test"))
}

publishing {
    repositories {
        maven {
            name = "ktor-hyperscript-dsl"
            version = System.getenv("PACKAGE_VERSION") ?: project.version
            url = uri("https://maven.pkg.github.com/rafaelladi/ktor-hyperscript-dsl")
            credentials {
                username = project.findProperty("gpr.user")?.toString() ?: System.getenv("GITHUB_USER")
                password = project.findProperty("gpr.key")?.toString() ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}