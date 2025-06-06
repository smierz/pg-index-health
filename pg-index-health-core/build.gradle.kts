plugins {
    id("pg-index-health.java-library")
}

description = "pg-index-health-core is a Java library for analyzing and maintaining indexes and tables health in PostgreSQL databases on a specific host."

dependencies {
    api(project(":pg-index-health-model"))
    api(project(":pg-index-health-jdbc-connection"))

    testImplementation(project(":pg-index-health-testing"))
    testImplementation(testFixtures(project(":pg-index-health-model")))
    testImplementation(testFixtures(project(":pg-index-health-jdbc-connection")))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.mockito:mockito-core")
    testImplementation(libs.postgresql)

    testFixturesImplementation(libs.jspecify)
    testFixturesImplementation(libs.apache.commons.lang3)
    testFixturesImplementation(libs.postgresql)
    testFixturesImplementation(project(":pg-index-health-testing"))
}
