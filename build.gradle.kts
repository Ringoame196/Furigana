plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.github.ringoame196"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okhttp3:okhttp:4.9.3") // okhttp
    implementation("org.json:json:20210307") // JSON処理ライブラリ
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "$group.MainKt"
    }
    configurations["compileClasspath"].forEach {
            file: File -> from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}