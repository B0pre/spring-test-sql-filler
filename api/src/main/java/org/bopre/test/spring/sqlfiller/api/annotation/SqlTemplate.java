package org.bopre.test.spring.sqlfiller.api.annotation;


import org.bopre.test.spring.sqlfiller.api.annotation.repeat.SqlTemplates;

import java.lang.annotation.*;

/**
 * Sql template definition
 */
@Repeatable(SqlTemplates.class)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlTemplate {

    /**
     * path to resource with sql template (before test method)
     *
     * @return
     */
    String template() default "";

    /**
     * path to resource with sql template (cleanup after test method)
     *
     * @return
     */
    String cleanupTemplate() default "";

    /**
     * inlined sql query (before test method)
     *
     * @return
     */
    String inlineSql() default "";

    /**
     * inlined sql query (cleanup after test method)
     *
     * @return
     */
    String inlineCleanupSql() default "";

    /**
     * >= 0
     *
     * @return
     */
    int order() default 0;

    /**
     * argument`s defaults replacement
     * {@link SqlArg}
     *
     * @return
     */
    SqlArg[] args() default {};

}
