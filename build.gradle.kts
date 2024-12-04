import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.21"
}

group = "dev.eric.advent-of-code-2024"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_22
    }
}