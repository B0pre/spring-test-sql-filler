package org.bopre.test.spring.sqlfiller.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

//TODO: rename
public class ContextListener extends AbstractTestExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        logger.info("==================== do magic before method ==============================");
        //TODO: do magic here
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        logger.info("==================== do magic after method ==============================");
        //TODO: do magic here
    }

}
