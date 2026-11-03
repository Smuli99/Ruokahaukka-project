plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
}

group = "com.ruokkis"
version = "1.0.0"
application {
    mainClass = "com.ruokkis.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation("io.ktor:ktor-server-content-negotiation:3.6.0")
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation("io.ktor:ktor-server-status-pages:3.6.0")
//    DB
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgresql)
    implementation(libs.hikari.cb)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgresql)
//    TEST
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.h2)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.ktor.client.mock)
}

tasks.test {
    useJUnitPlatform()
}