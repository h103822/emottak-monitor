import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "no.nav.emottak"
version = "1.0.0"

val coroutinesVersion = "1.2.2"
val jacksonVersion = "2.9.7"
val kluentVersion = "1.39"
val ktorVersion = "1.3.1"
val logbackVersion = "1.2.3"
val logstashEncoderVersion = "6.1"
val prometheusVersion = "0.5.0"
val micrometerRegistryPrometheusVersion = "1.1.5"
val junitJupiterVersion = "5.6.0"
val ojdbc8Version = "19.3.0.0"
val hikariVersion = "3.3.1"

plugins {
    kotlin("jvm") version "1.3.50"
    id("org.jmailen.kotlinter") version "2.1.1"
    id("com.diffplug.gradle.spotless") version "3.24.0"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/ktor")
    maven(url = "https://dl.bintray.com/spekframework/spek-dev")
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://repo1.maven.org/maven2/")
    maven(url = "https://oss.sonatype.org/content/groups/staging/")

}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutinesVersion")
    implementation ("io.micrometer:micrometer-registry-prometheus:$micrometerRegistryPrometheusVersion")

    implementation ("io.ktor:ktor-server-netty:$ktorVersion")
    implementation ("io.ktor:ktor-jackson:$ktorVersion")

    implementation ("ch.qos.logback:logback-classic:$logbackVersion")
    implementation ("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    implementation ("com.zaxxer:HikariCP:$hikariVersion")
    implementation ("com.oracle.ojdbc:ojdbc8:$ojdbc8Version")

    testImplementation ("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation ("org.amshove.kluent:kluent:$kluentVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
       testImplementation("io.ktor:ktor-server-test-host:$ktorVersion") {
        exclude(group = "org.eclipse.jetty")
    }
}


tasks {
    withType<Jar> {
        manifest.attributes["Main-Class"] = "no.nav.emottak.BootstrapKt"
    }

    create("printVersion") {

        doLast {
            println(project.version)
        }
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Test> {
        useJUnit()
        testLogging {
            showStandardStreams = true
        }
    }

    "check" {
        dependsOn("formatKotlin")
    }
}
