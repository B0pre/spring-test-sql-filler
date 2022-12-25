package org.bopre.test.spring.sqlfiller.context;

import org.springframework.test.context.TestContext;

import javax.sql.DataSource;

public class ExecutionContextAdapter implements ExecutionContext {

    private final TestContext testContext;

    public ExecutionContextAdapter(TestContext testContext) {
        this.testContext = testContext;
    }

    @Override
    public DataSource getDataSource() {
        return testContext.getApplicationContext().getBean(DataSource.class);
    }

    public static ExecutionContext of(TestContext testContext) {
        return new ExecutionContextAdapter(testContext);
    }

}
