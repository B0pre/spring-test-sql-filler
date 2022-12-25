package org.bopre.test.spring.sqlfiller.context.processing.impl;

import org.bopre.test.spring.sqlfiller.context.processing.SqlTemplateProperties;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class SqlTemplatePropertiesMapBasedImpl implements SqlTemplateProperties {

    private final Map<String, String> properties;

    private SqlTemplatePropertiesMapBasedImpl(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public Optional<String> getProperty(String name) {
        return Optional.ofNullable(properties.get(name));
    }

    public static SqlTemplateProperties create(Map<String, String> properties) {
        return new SqlTemplatePropertiesMapBasedImpl(
                Collections.unmodifiableMap(properties)
        );
    }

}
