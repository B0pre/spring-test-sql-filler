package org.bopre.test.spring.sqlfiller.context;

import org.bopre.test.spring.sqlfiller.context.execution.ExecutionStrategyAbstractFactory;
import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.context.search.PreparationsSearcher;
import org.bopre.test.spring.sqlfiller.utils.io.SimpleCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.Collection;

public class SqlTemplateTestExecutionListener extends AbstractTestExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(SqlTemplateTestExecutionListener.class);
    private final PreparationsSearcher preparationsSearcher = PreparationsSearcher.init();
    private final ExecutionStrategyAbstractFactory executionStrategyFactory = ExecutionStrategyAbstractFactory.init();

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        final Collection<SqlPreparation> sqlPreparations = getPreparations(testContext);
        tryOrSetDirty(testContext, () -> {
            executionStrategyFactory
                    .beforeTest()
                    .executeAll(ExecutionContextAdapter.of(testContext), sqlPreparations);
        });
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        final Collection<SqlPreparation> sqlPreparations = getPreparations(testContext);
        tryOrSetDirty(testContext, () -> {
            executionStrategyFactory
                    .afterTest()
                    .executeAll(ExecutionContextAdapter.of(testContext), sqlPreparations);
        });
    }

    private Collection<SqlPreparation> getPreparations(TestContext testContext) {
        return preparationsSearcher.findPreparations(testContext.getTestMethod().getAnnotations());
    }

    private void tryOrSetDirty(TestContext context, SimpleCommand command) {
        try {
            command.doCommand();
        } catch (Exception e) {
            logger.warn("failed, set context dirty");
            context.markApplicationContextDirty(DirtiesContext.HierarchyMode.EXHAUSTIVE);
            throw e;
        }
    }

}
