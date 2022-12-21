package org.bopre.test.spring.sqlfiller.annotation;

import org.bopre.test.spring.sqlfiller.context.ContextListener;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestExecutionListeners(
        value = {ContextListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public @interface EnableSqlFiller {
}
