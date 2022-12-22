package org.bopre.test.spring.sqlfiller.api.annotation;

import org.bopre.test.spring.sqlfiller.api.annotation.repeat.TemplatePacks;

import java.lang.annotation.*;

/**
 * Custom annotations support
 */
@Repeatable(TemplatePacks.class)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlTemplatePack {

    /**
     * custom annotation with sql templates
     *
     * @return
     */
    Class<? extends Annotation> value();

    /**
     * argument`s defaults replacement
     * {@link SqlArg}
     *
     * @return
     */
    SqlArg[] args() default {};

    /**
     * >= 0
     *
     * @return
     */
    int order() default 0;

}
