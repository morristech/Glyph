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

ext {
    set("bintrayRepo", "maven")
    set("bintrayName", "glyph")

    set("publishedGroupId", "io.lamart.krate")
    set("libraryName", "Glyph")
    set("artifact", "glyph")
    set("libraryDescription", "State management made easy.")

    set("siteUrl", "https://github.com/Lamartio/Glyph/")
    set("gitUrl", "https://github.com/Lamartio/Glyph.git")

    set("libraryVersion", "0.5.0")

    set("developerId", "Lamartio")
    set("developerName", "Danny Lamarti")
    set("developerEmail", "")

    set("licenseName", "MIT")
    set("licenseUrl", "https://opensource.org/licenses/MIT")
    set("allLicenses", arrayOf("MIT"))
}