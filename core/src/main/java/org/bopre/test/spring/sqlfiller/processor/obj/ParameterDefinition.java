package org.bopre.test.spring.sqlfiller.processor.obj;

import java.util.Objects;

public class ParameterDefinition {

    private final SupportedType type;
    private final int index;
    private final String name;
    private final String defaultValue;

    public ParameterDefinition(SupportedType type, int index, String name, String defaultValue) {
        this.type = type;
        this.index = index;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public SupportedType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterDefinition that = (ParameterDefinition) o;
        return index == that.index && type == that.type && Objects.equals(name, that.name) && Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, index, name, defaultValue);
    }

    @Override
    public String toString() {
        return "?" + index + " " + name + "::" + type + " = " + defaultValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SupportedType type;
        private int index;
        private String name;
        private String defaultValue;

        public Builder type(SupportedType type) {
            this.type = type;
            return this;
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder defaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ParameterDefinition build() {
            return new ParameterDefinition(type, index, name, defaultValue);
        }
    }

}
