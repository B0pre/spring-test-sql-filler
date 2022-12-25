package org.bopre.test.spring.sqlfiller.context.processing.impl;

import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.context.processing.SqlTemplateProperties;

import java.util.Optional;

public class SqlPreparationImpl implements SqlPreparation {
    private final String sqlTemplate;
    private final String sqlCleanup;
    private final SqlTemplateProperties properties;

    public SqlPreparationImpl(String sqlTemplate, String sqlCleanup, SqlTemplateProperties properties) {
        this.sqlTemplate = sqlTemplate;
        this.sqlCleanup = sqlCleanup;
        this.properties = properties;
    }

    @Override
    public Optional<String> getSqlTemplate() {
        return Optional.ofNullable(sqlTemplate);
    }

    @Override
    public Optional<String> getSqlCleanup() {
        return Optional.ofNullable(sqlCleanup);
    }

    @Override
    public SqlTemplateProperties getProperties() {
        return properties;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sqlTemplate;
        private String sqlCleanup;
        private SqlTemplateProperties properties;

        public Builder sqlTemplate(String sqlTemplate) {
            this.sqlTemplate = sqlTemplate;
            return this;
        }

        public Builder sqlCleanup(String sqlCleanup) {
            this.sqlCleanup = sqlCleanup;
            return this;
        }

        public Builder properties(SqlTemplateProperties properties) {
            this.properties = properties;
            return this;
        }

        public SqlPreparation build() {
            return new SqlPreparationImpl(sqlTemplate, sqlCleanup, properties);
        }

    }

}
