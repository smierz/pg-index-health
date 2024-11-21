/*
 * Copyright (c) 2019-2024. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.checks.cluster;

import io.github.mfvanek.pg.connection.HighAvailabilityPgConnection;
import io.github.mfvanek.pg.core.checks.host.ForeignKeysNotCoveredWithIndexCheckOnHost;
import io.github.mfvanek.pg.model.constraint.ForeignKey;

import javax.annotation.Nonnull;

/**
 * Check for foreign keys without associated indexes on all hosts in the cluster.
 *
 * @author Ivan Vahrushev
 * @since 0.6.0
 */
public class ForeignKeysNotCoveredWithIndexCheckOnCluster extends AbstractCheckOnCluster<ForeignKey> {

    public ForeignKeysNotCoveredWithIndexCheckOnCluster(@Nonnull final HighAvailabilityPgConnection haPgConnection) {
        super(haPgConnection, ForeignKeysNotCoveredWithIndexCheckOnHost::new);
    }
}
