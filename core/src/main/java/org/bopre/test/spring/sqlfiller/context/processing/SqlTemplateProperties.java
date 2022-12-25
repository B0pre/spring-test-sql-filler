package org.bopre.test.spring.sqlfiller.context.processing;

import org.bopre.test.spring.sqlfiller.context.processing.impl.SqlTemplatePropertiesMapBasedImpl;

import java.util.Map;
import java.util.Optional;

public interface SqlTemplateProperties {
    Optional<String> getProperty(String name);

    static SqlTemplateProperties empty() {
        return name -> Optional.empty();
    }

    static SqlTemplateProperties ofMap(Map<String, String> properties) {
        return SqlTemplatePropertiesMapBasedImpl.create(properties);
    }

    /**
     * create superposition of basic and additional properties
     * (firstly try to find property in basic if nothing found search is delegated to additional properties)
     *
     * @param basic      - primarily search property here
     * @param additional - search here if nothing found in {@code basic}
     * @return
     */
    static SqlTemplateProperties superposition(SqlTemplateProperties basic, SqlTemplateProperties additional) {
        return name -> {
            Optional<String> value = basic.getProperty(name);
            if (!value.isPresent())
                value = additional.getProperty(name);
            return value;
        };
    }

}
