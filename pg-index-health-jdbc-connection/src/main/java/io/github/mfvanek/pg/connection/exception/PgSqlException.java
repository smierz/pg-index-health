/*
 * Copyright (c) 2019-2025. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.connection.exception;

import java.sql.SQLException;

/**
 * Custom unchecked exception for SQL errors.
 *
 * @author Ivan Vakhrushev
 * @since 0.5.0
 */
public class PgSqlException extends RuntimeException {

    private static final long serialVersionUID = -8740709401886468019L;

    /**
     * Constructs a new {@code PgSqlException} with the specified cause.
     *
     * @param cause the {@link SQLException} that caused this exception; must not be {@code null}.
     */
    public PgSqlException(final SQLException cause) {
        super(cause.getMessage(), cause);
    }
}
