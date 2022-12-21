package org.bopre.test.spring.sqlfiller.processor.obj;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TemplateProcessingResult {

    private final String originTemplate;
    private final String sql;
    private final List<ParameterDefinition> parameters;

    public TemplateProcessingResult(String originTemplate, String sql, List<ParameterDefinition> parameters) {
        this.originTemplate = originTemplate;
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getOriginTemplate() {
        return originTemplate;
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterDefinition> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String originTemplate;
        private String sql;
        private List<ParameterDefinition> parameters = new LinkedList<>();

        public Builder originTemplate(String originTemplate) {
            this.originTemplate = originTemplate;
            return this;
        }

        public Builder sql(String sql) {
            this.sql = sql;
            return this;
        }

        public Builder parameters(List<ParameterDefinition> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder parameter(ParameterDefinition parameter) {
            this.parameters.add(parameter);
            return this;
        }

        public TemplateProcessingResult build() {
            return new TemplateProcessingResult(originTemplate, sql, parameters);
        }
    }

}
