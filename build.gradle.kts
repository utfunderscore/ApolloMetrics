plugins {
    id("java-library")
    id("maven-publish")
}

group = "com.readutf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.readutf.applometrics"
            artifactId = "applometrics"
            version = "1.0.0"
            from(components["java"])
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    api("com.influxdb:influxdb-client-java:7.0.0")

    api("org.mongodb:mongodb-driver-sync:5.0.0")

    implementation("com.google.guava:guava:33.1.0-jre")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

}

tasks.test {
    useJUnitPlatform()
}