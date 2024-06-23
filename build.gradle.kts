plugins {
    kotlin("jvm") version "1.9.24"
    java
    `maven-publish`
    id("net.nemerosa.versioning") version "3.1.0"
}

group = "fe.mimetypekt"
version = versioning.info.tag ?: versioning.info.full

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.gitlab.grrfe.gson-ext:core:16.0.0-gson2-koin3")
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
