plugins {
    application
    kotlin("jvm") version "1.9.22"
}

group = "mvi.demo"
version = "0.1.0"

repositories { mavenCentral() }

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.22")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application { mainClass.set("mvvm.MainKt") }

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(17) }
