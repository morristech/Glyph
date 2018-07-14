private val kotlinVersion = "1.2.41"

group = "io.lamart.glyph"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    testImplementation("junit:junit:4.12")
}