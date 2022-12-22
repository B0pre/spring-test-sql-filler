package org.bopre.test.spring.sqlfiller.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Replace sql template argument`s default value
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlArg {

    /**
     * argument name
     *
     * @return
     */
    String name();

    /**
     * argument value
     *
     * @return
     */
    String value();

}
