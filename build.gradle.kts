plugins {
    id("java")
    id("application")
}

group = "com.ocado"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClassName = "com.ocado.basket.BasketSplitter"
}
tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
                "Main-Class" to "com.ocado.basket.BasketSplitter"
        )
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}