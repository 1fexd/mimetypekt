plugins {
    kotlin("jvm") version "1.9.0"
    java
    `maven-publish`
    id("net.nemerosa.versioning") version "3.0.0"
}

group = "fe.mimetypekt"
version = versioning.info.tag ?: versioning.info.full

repositories {
    mavenCentral()
    maven(url="https://jitpack.io")
}

dependencies {
    implementation("com.gitlab.grrfe:gson-ext:8.1.2")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
}

tasks.withType<Jar> {
    exclude(".idea", "venv", "fetch_latest_apache_tika_mimetypes.sh", "parse-mimetypes.py", "tika-mimetypes.xml")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            version = project.version.toString()

            from(components["java"])
        }
    }
}
