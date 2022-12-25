package org.bopre.test.spring.sqlfiller.context.execution.strategy;

import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.utils.ordering.ComparableWrap;

import java.util.Comparator;
import java.util.Optional;

public class BeforeExecutionStrategyImpl extends AbstractExecutionStrategy {
    @Override
    protected Comparator<ComparableWrap<SqlPreparation>> sortPreparationsBy() {
        return Comparator.naturalOrder();
    }

    @Override
    protected Optional<String> getSqlTemplate(SqlPreparation sqlPreparation) {
        return sqlPreparation.getSqlTemplate();
    }

}
