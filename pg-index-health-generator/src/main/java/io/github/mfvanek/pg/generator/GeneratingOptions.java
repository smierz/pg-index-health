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

import java.util.Objects;

/**
 * Immutable options to generate sql queries for database migration.
 *
 * @author Ivan Vakhrushev
 * @since 0.5.0
 */
public class GeneratingOptions {

    /**
     * Neediness to build indexes concurrently.
     */
    private final boolean concurrently;
    /**
     * Neediness to exclude null values from indexes to be built.
     */
    private final boolean excludeNulls;
    /**
     * Neediness to break long generated sql queries into lines.
     */
    private final boolean breakLines;
    /**
     * Indentation size for new lines.
     */
    private final int indentation;
    /**
     * Neediness to use capital letters for SQL operators and keywords.
     */
    private final boolean uppercaseForKeywords;
    /**
     * Neediness to add "without_nulls" part to the generated index name.
     */
    private final boolean nameWithoutNulls;
    /**
     * Position of "idx" in the generated index name.
     */
    private final IdxPosition idxPosition;

    private GeneratingOptions(final boolean concurrently,
                              final boolean excludeNulls,
                              final boolean breakLines,
                              final int indentation,
                              final boolean uppercaseForKeywords,
                              final boolean nameWithoutNulls,
                              final IdxPosition idxPosition) {
        this.concurrently = concurrently;
        this.excludeNulls = excludeNulls;
        this.breakLines = breakLines;
        this.indentation = indentation;
        this.uppercaseForKeywords = uppercaseForKeywords;
        this.nameWithoutNulls = nameWithoutNulls;
        this.idxPosition = idxPosition;
    }

    public boolean isConcurrently() {
        return concurrently;
    }

    public boolean isExcludeNulls() {
        return excludeNulls;
    }

    public boolean isBreakLines() {
        return breakLines;
    }

    public int getIndentation() {
        return indentation;
    }

    public boolean isUppercaseForKeywords() {
        return uppercaseForKeywords;
    }

    public boolean isNameWithoutNulls() {
        return nameWithoutNulls;
    }

    public IdxPosition getIdxPosition() {
        return idxPosition;
    }

    public boolean isNeedToAddIdx() {
        return idxPosition != IdxPosition.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return GeneratingOptions.class.getSimpleName() + '{' +
            "concurrently=" + concurrently +
            ", excludeNulls=" + excludeNulls +
            ", breakLines=" + breakLines +
            ", indentation=" + indentation +
            ", uppercaseForKeywords=" + uppercaseForKeywords +
            ", nameWithoutNulls=" + nameWithoutNulls +
            ", idxPosition=" + idxPosition +
            '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean concurrently = true;
        private boolean excludeNulls = true;
        private boolean breakLines = true;
        private int indentation = 4;
        private boolean uppercaseForKeywords;
        private boolean nameWithoutNulls = true;
        private IdxPosition idxPosition = IdxPosition.SUFFIX;

        private Builder() {
        }

        public GeneratingOptions build() {
            return new GeneratingOptions(concurrently, excludeNulls, breakLines, indentation, uppercaseForKeywords, nameWithoutNulls, idxPosition);
        }

        /**
         * Use concurrent index building without table locking.
         *
         * @return builder object
         */
        public Builder concurrently() {
            this.concurrently = true;
            return this;
        }

        /**
         * Use regular index building with table locking.
         *
         * @return builder object
         */
        public Builder normally() {
            this.concurrently = false;
            return this;
        }

        public Builder excludeNulls() {
            this.excludeNulls = true;
            return this;
        }

        public Builder includeNulls() {
            this.excludeNulls = false;
            return this;
        }

        public Builder breakLines() {
            this.breakLines = true;
            return this;
        }

        public Builder doNotBreakLines() {
            this.breakLines = false;
            return this;
        }

        public Builder withIndentation(final int indentation) {
            this.indentation = validateIndentation(indentation);
            return this;
        }

        public Builder uppercaseForKeywords() {
            this.uppercaseForKeywords = true;
            return this;
        }

        public Builder lowercaseForKeywords() {
            this.uppercaseForKeywords = false;
            return this;
        }

        public Builder nameWithoutNulls() {
            this.nameWithoutNulls = true;
            return this;
        }

        public Builder doNotNameWithoutNulls() {
            this.nameWithoutNulls = false;
            return this;
        }

        public Builder withIdxPosition(final IdxPosition idxPosition) {
            this.idxPosition = Objects.requireNonNull(idxPosition, "idxPosition cannot be null");
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return Builder.class.getSimpleName() + '{' +
                "concurrently=" + concurrently +
                ", excludeNulls=" + excludeNulls +
                ", breakLines=" + breakLines +
                ", indentation=" + indentation +
                ", uppercaseForKeywords=" + uppercaseForKeywords +
                ", nameWithoutNulls=" + nameWithoutNulls +
                ", idxPosition=" + idxPosition +
                '}';
        }

        private static int validateIndentation(final int indentation) {
            if (indentation < 0 || indentation > 8) {
                throw new IllegalArgumentException("indentation should be in the range [0, 8]");
            }
            return indentation;
        }
    }
}
