package org.bopre.test.spring.sqlfiller.context.execution;

import org.bopre.test.spring.sqlfiller.context.execution.strategy.AfterExecutionStrategyImpl;
import org.bopre.test.spring.sqlfiller.context.execution.strategy.BeforeExecutionStrategyImpl;

public interface ExecutionStrategyAbstractFactory {

    ExecutionStrategy beforeTest();

    ExecutionStrategy afterTest();

    static ExecutionStrategyAbstractFactory init() {
        return new ExecutionStrategyAbstractFactory() {
            @Override
            public ExecutionStrategy beforeTest() {
                return new BeforeExecutionStrategyImpl();
            }

            @Override
            public ExecutionStrategy afterTest() {
                return new AfterExecutionStrategyImpl();
            }
        };
    }

}
