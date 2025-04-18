/*
 * Copyright (c) 2019-2025. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.model.table;

import io.github.mfvanek.pg.model.context.PgContext;
import io.github.mfvanek.pg.model.validation.Validators;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Representation of a table in a database with additional information on reads amount via index or sequential scans.
 *
 * @author Ivan Vakhrushev
 */
@Immutable
public final class TableWithMissingIndex extends AbstractTableAware implements Comparable<TableWithMissingIndex> {

    /**
     * The number of sequential scans performed on the table.
     * Normally, indexes should be used primarily when accessing a table.
     * If there are few or no indexes in the table, then seqScans will be larger than indexScans.
     */
    private final long seqScans;

    /**
     * The number of index scans performed on the table.
     */
    private final long indexScans;

    private TableWithMissingIndex(@Nonnull final Table table,
                                  final long seqScans,
                                  final long indexScans) {
        super(table);
        this.seqScans = Validators.countNotNegative(seqScans, "seqScans");
        this.indexScans = Validators.countNotNegative(indexScans, "indexScans");
    }

    /**
     * Retrieves the number of sequential scans performed on this table.
     *
     * @return the sequential scan count
     */
    public long getSeqScans() {
        return seqScans;
    }

    /**
     * Retrieves the number of index scans performed on this table.
     *
     * @return the index scan count
     */
    public long getIndexScans() {
        return indexScans;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return TableWithMissingIndex.class.getSimpleName() + '{' +
            table.innerToString() +
            ", seqScans=" + seqScans +
            ", indexScans=" + indexScans +
            '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof TableWithMissingIndex)) {
            return false;
        }

        final TableWithMissingIndex that = (TableWithMissingIndex) other;
        return Objects.equals(table, that.table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(@Nonnull final TableWithMissingIndex other) {
        Objects.requireNonNull(other, "other cannot be null");
        return table.compareTo(other.table);
    }

    /**
     * Constructs a {@code TableWithMissingIndex} object.
     *
     * @param tableName        table name; should be non-blank.
     * @param tableSizeInBytes table size in bytes; should be positive or zero.
     * @param seqScans         number of sequential scans initiated on this table; should be non-negative.
     * @param indexScans       number of index scans initiated on this table; should be non-negative.
     * @return {@code TableWithMissingIndex}
     */
    @Nonnull
    public static TableWithMissingIndex of(@Nonnull final String tableName,
                                           final long tableSizeInBytes,
                                           final long seqScans,
                                           final long indexScans) {
        final Table table = Table.of(tableName, tableSizeInBytes);
        return of(table, seqScans, indexScans);
    }

    /**
     * Constructs a {@code TableWithMissingIndex} object with given context.
     *
     * @param pgContext        the schema context to enrich table name; must be non-null.
     * @param tableName        table name; should be non-blank.
     * @param tableSizeInBytes table size in bytes; should be positive or zero.
     * @param seqScans         number of sequential scans initiated on this table; should be non-negative.
     * @param indexScans       number of index scans initiated on this table; should be non-negative.
     * @return {@code TableWithMissingIndex}
     * @since 0.14.3
     */
    @Nonnull
    public static TableWithMissingIndex of(@Nonnull final PgContext pgContext,
                                           @Nonnull final String tableName,
                                           final long tableSizeInBytes,
                                           final long seqScans,
                                           final long indexScans) {
        final Table table = Table.of(pgContext, tableName, tableSizeInBytes);
        return of(table, seqScans, indexScans);
    }

    /**
     * Constructs a {@code TableWithMissingIndex} object.
     *
     * @param table      table; should be non-null.
     * @param seqScans   number of sequential scans initiated on this table; should be non-negative.
     * @param indexScans number of index scans initiated on this table; should be non-negative.
     * @return {@code TableWithMissingIndex}
     * @since 0.7.0
     */
    @Nonnull
    public static TableWithMissingIndex of(@Nonnull final Table table,
                                           final long seqScans,
                                           final long indexScans) {
        return new TableWithMissingIndex(table, seqScans, indexScans);
    }
}
