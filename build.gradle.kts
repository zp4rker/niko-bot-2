plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"

    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.zp4rker"
version = "1.0.0-alpha"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.charleskorn.kaml:kaml:0.46.0")

    implementation("net.dv8tion:JDA:5.0.0-alpha.17")
    implementation("com.zp4rker:log4kt:1.1.8")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.zp4rker.nikobot.ii.BotKt"
        attributes["Implementation-Version"] = project.version
    }
}

tasks.build { dependsOn("shadowJar") }
tasks.shadowJar {
    archiveClassifier.set("")
}