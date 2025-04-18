/*
 * Copyright (c) 2019-2025. Ivan Vakhrushev and others.
 * https://github.com/mfvanek/pg-index-health
 *
 * This file is a part of "pg-index-health" - a Java library for
 * analyzing and maintaining indexes health in PostgreSQL databases.
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.model.settings;

import io.github.mfvanek.pg.model.settings.validation.ParamValidators;
import io.github.mfvanek.pg.model.validation.Validators;

import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Deprecated for removal.
 *
 * @deprecated since 0.14.6
 */
@Deprecated(forRemoval = true)
public class PgParamImpl implements PgParam {

    private final String name;
    private final String value;

    private PgParamImpl(@Nonnull final String name, @Nonnull final String value) {
        this.name = Validators.notBlank(name, "name");
        this.value = ParamValidators.paramValueNotNull(value, "value for '" + name + "' cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    @Nonnull
    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return PgParamImpl.class.getSimpleName() + '{' +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PgParam)) {
            return false;
        }

        final PgParam that = (PgParam) other;
        return Objects.equals(name, that.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return Objects.hash(name);
    }

    public static PgParam of(@Nonnull final String name, @Nonnull final String value) {
        return new PgParamImpl(name, value);
    }
}
