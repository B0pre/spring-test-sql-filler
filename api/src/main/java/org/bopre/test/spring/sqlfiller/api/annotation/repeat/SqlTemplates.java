package org.bopre.test.spring.sqlfiller.api.annotation.repeat;

import org.bopre.test.spring.sqlfiller.api.annotation.SqlTemplate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * repeat {@link SqlTemplate} support
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlTemplates {

    SqlTemplate[] value();

}
