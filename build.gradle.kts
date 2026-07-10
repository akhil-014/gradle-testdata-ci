plugins {
    java
    id("io.qameta.allure") version "2.12.0"
}

group = "com.ust.sdet"
version = "1.0-SNAPSHOT"

val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.14.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val slf4jVersion = "2.0.17"
val testcontainersVersion = "2.0.5"
val flywayVersion = "10.22.0"
val mysqlVersion = "9.3.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers-mysql")
    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.flywaydb:flyway-mysql:$flywayVersion")
    testImplementation("com.mysql:mysql-connector-j:$mysqlVersion")
    testImplementation("io.qameta.allure:allure-junit5:${allureVersion}")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("baseUrl", providers.gradleProperty("baseUrl").orElse("http://localhost:5173").get())
    systemProperty("headless", providers.gradleProperty("headless").orElse("false").get())
    systemProperty("browser", providers.gradleProperty("browser").orElse("chrome").get())
    systemProperty("build.label", providers.gradleProperty("buildLabel").orElse("gradle-local").get())
    systemProperty("cucumber.publish.quiet", "true")
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
    }
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

tasks.test {
    description = "Runs the main Selenium/JUnit regression tests."
    group = "verification"
    useJUnitPlatform()
//    include("**/OrderTest.class", "**/SmokeTest.class", "**/AllureReportInsightTest.class", "**/AllureCategoryTriggerTest.class")
    include("**/AllureCategoryTriggerTest.class")
    maxParallelForks = 1
}

val SmokeTest by tasks.registering(Test::class) {
    description = "Runs the safe no-browser Week 6 Day 1 structure checks."
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/SmokeTest.class")
}

val OrderTest by tasks.registering(Test::class) {
    description = "W6D3 - Overnight"
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/OrderTest.class")
}

val AllureReportInsightTest by tasks.registering(Test::class) {
    description = "W6D4 - Overnight"
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/AllureReportInsightTest.class")
}

val AllureCategoryTriggerTest by tasks.registering(Test::class) {
    description = "W6D4 - Overnight"
    group = "verification"
    useProjectTestClasses()
    useJUnitPlatform()
    include("**/AllureCategoryTriggerTest.class")
}

val copyAllureCategories by tasks.registering(Copy::class) {
    from("src/test/resources/allure/categories.json")
    into("build/allure-results")
}

tasks.test {
    doLast {
        copy {
            from("src/test/resources/allure/categories.json")
            into("build/allure-results")
        }
    }
}

tasks.register("projectBuildSummary") {
    description = "Prints the Gradle command map for this project."
    group = "help"
    doLast {
        println(
            """
            Project Build Summary
            Gradle compile: ./gradlew clean testClasses
            Gradle main tests: ./gradlew test
            Gradle order test: ./gradlew OrderTest
            Gradle smoke: ./gradlew SmokeTest
            Gradle report: ./gradlew AllureReportInsightTest
            Gradle category report: ./gradlew AllureCategoryTriggerTest
            """.trimIndent()
        )
    }
}