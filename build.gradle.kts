buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin", "1.2.41"))
    }
}

group = "io.lamart.glyph"
version = "0.9.0"

plugins { `java-library` }

apply {
    plugin("kotlin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlinModule("stdlib-jdk8", "1.2.31"))
    testImplementation("junit:junit:4.12")
}