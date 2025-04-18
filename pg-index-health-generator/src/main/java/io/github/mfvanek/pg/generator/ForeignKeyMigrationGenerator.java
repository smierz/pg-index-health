/*
 * Copyright (c) 2019-2025. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.generator;

import io.github.mfvanek.pg.model.constraint.ForeignKey;

import javax.annotation.Nonnull;

/**
 * Migration generator for creating indexes covering foreign keys.
 *
 * @author Ivan Vakhrushev
 * @since 0.5.0
 */
public class ForeignKeyMigrationGenerator extends AbstractDbMigrationGenerator<ForeignKey> {

    private final PgIndexOnForeignKeyGenerator generator;

    public ForeignKeyMigrationGenerator(@Nonnull final GeneratingOptions options) {
        this.generator = new PgIndexOnForeignKeyGenerator(options);
    }

    @Override
    @Nonnull
    protected String generate(@Nonnull final ForeignKey foreignKey) {
        return generator.generate(foreignKey);
    }
}
