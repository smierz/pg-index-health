/*
 * Copyright (c) 2019-2024. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.Nullable;

/**
 * Custom {@link SpringBootCondition} to disable starter when configured a data source to another database (not PostgreSQL).
 */
public class DatabaseStructureHealthCondition extends SpringBootCondition {

    /**
     * The URL prefix used in Testcontainers to initialize PostgreSQL containers.
     * <p>
     * Testcontainers provides a special JDBC URL format that allows for on-the-fly creation and management
     * of PostgreSQL database containers during tests. This prefix is part of the JDBC URL and signals
     * Testcontainers to handle the lifecycle of the container automatically.
     * </p>
     *
     * @see <a href="https://java.testcontainers.org/modules/databases/jdbc/">Testcontainers JDBC Support</a>
     */
    static final String TESTCONTAINERS_PG_URL_PREFIX = "jdbc:tc:postgresql:";

    private static final String ORIGINAL_PG_URL_PREFIX = "jdbc:postgresql://";
    private static final String PROPERTY_NAME = "spring.datasource.url";

    /**
     * {@inheritDoc}
     */
    @Override
    public ConditionOutcome getMatchOutcome(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        final ConditionMessage.Builder message = ConditionMessage.forCondition("pg.index.health.test PostgreSQL condition");
        final String jdbcUrl = getJdbcUrl(context);
        if (jdbcUrl != null && !jdbcUrl.isBlank()) {
            if (jdbcUrl.startsWith(ORIGINAL_PG_URL_PREFIX) || jdbcUrl.startsWith(TESTCONTAINERS_PG_URL_PREFIX)) {
                return ConditionOutcome.match(message.foundExactly("found PostgreSQL connection " + jdbcUrl));
            }
            return ConditionOutcome.noMatch(message.notAvailable("not PostgreSQL connection"));
        }
        return ConditionOutcome.match(message.didNotFind(PROPERTY_NAME).items());
    }

    @Nullable
    private static String getJdbcUrl(final ConditionContext context) {
        return Binder.get(context.getEnvironment())
            .bind(PROPERTY_NAME, String.class)
            .orElse(null);
    }
}
