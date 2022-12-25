package org.bopre.test.spring.sqlfiller.context.execution;

import org.bopre.test.spring.sqlfiller.context.ExecutionContext;
import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.exception.SqlPreparationException;

import java.util.Collection;

public interface ExecutionStrategy {

    void executeAll(ExecutionContext context, Collection<SqlPreparation> sqlPreparations) throws SqlPreparationException;

}
