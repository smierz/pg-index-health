plugins {
    id("pg-index-health.java-library")
    id("pg-index-health.pitest")
}

description = "pg-index-health-generator is an extension for generating database migrations in sql format based on pg-index-health diagnostics."

dependencies {
    api(project(":pg-index-health-model"))

    testImplementation(testFixtures(project(":pg-index-health-model")))
    testImplementation(testFixtures(project(":pg-index-health-jdbc-connection")))
}
