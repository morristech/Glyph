group = "io.lamart.glyph"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.70")
    }
}

plugins {
    java
}

apply {
    plugin("java-library")
    plugin("kotlin")
    plugin("maven")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.2.70")
    testImplementation("junit:junit:4.12")
}