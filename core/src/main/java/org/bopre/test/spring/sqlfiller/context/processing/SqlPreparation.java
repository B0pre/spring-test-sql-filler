package org.bopre.test.spring.sqlfiller.context.processing;

import java.util.Optional;

public interface SqlPreparation {
    Optional<String> getSqlTemplate();

    Optional<String> getSqlCleanup();

    SqlTemplateProperties getProperties();

}
