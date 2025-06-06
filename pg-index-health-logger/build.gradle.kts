plugins {
    id("pg-index-health.java-library")
}

description = "pg-index-health-logger is a Java library for collecting and logging health state in PostgreSQL databases."

dependencies {
    api(project(":pg-index-health-model"))
    api(project(":pg-index-health-jdbc-connection"))
    api(project(":pg-index-health-core"))
    api(project(":pg-index-health"))

    testImplementation(testFixtures(project(":pg-index-health-core")))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.mockito:mockito-core")
    testImplementation(libs.postgresql)

    testCompileOnly(libs.forbiddenapis)
}
