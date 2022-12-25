package org.bopre.test.spring.sqlfiller.context.execution.strategy;

import org.bopre.test.spring.sqlfiller.context.ExecutionContext;
import org.bopre.test.spring.sqlfiller.context.execution.ExecutionStrategy;
import org.bopre.test.spring.sqlfiller.context.execution.type.TypeBehaviourFactory;
import org.bopre.test.spring.sqlfiller.context.execution.type.TypeBehaviourFactoryImpl;
import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.context.processing.SqlTemplateProperties;
import org.bopre.test.spring.sqlfiller.exception.SqlPreparationException;
import org.bopre.test.spring.sqlfiller.processor.SqlTemplateProcessor;
import org.bopre.test.spring.sqlfiller.processor.SqlTemplateProcessorImpl;
import org.bopre.test.spring.sqlfiller.processor.obj.ParameterDefinition;
import org.bopre.test.spring.sqlfiller.processor.obj.TemplateProcessingResult;
import org.bopre.test.spring.sqlfiller.utils.ordering.ComparableWrap;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractExecutionStrategy implements ExecutionStrategy {

    private final TypeBehaviourFactory typeBehaviourFactory = new TypeBehaviourFactoryImpl();
    private final SqlTemplateProcessor sqlTemplateProcessor = new SqlTemplateProcessorImpl();

    @Override
    public void executeAll(ExecutionContext context, Collection<SqlPreparation> sqlPreparations) throws SqlPreparationException {
        try {
            final AtomicInteger indexSeq = new AtomicInteger(0);

            sqlPreparations.stream()
                    .map(preparation -> new ComparableWrap<>(indexSeq.incrementAndGet(), preparation))
                    .sorted(sortPreparationsBy()) //sort according to strategy
                    .map(ComparableWrap::getInner)
                    .forEach(
                            preparation ->
                            {
                                final Optional<String> sql = getSqlTemplate(preparation);
                                //skip if no sql presented for preparation
                                sql.ifPresent(s -> execute(getDataSource(context), s, preparation.getProperties()));
                            }
                    );
        } catch (Exception e) {
            throw new SqlPreparationException(e);
        }
    }

    protected abstract Comparator<ComparableWrap<SqlPreparation>> sortPreparationsBy();

    protected abstract Optional<String> getSqlTemplate(SqlPreparation sqlPreparation);

    private void execute(DataSource dataSource, String sqlTemplate, SqlTemplateProperties properties) {
        try {
            final TemplateProcessingResult processingResult = sqlTemplateProcessor.processSql(sqlTemplate);
            final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(processingResult.getSql());

            //apply parameters for template
            for (ParameterDefinition parameterDefinition : processingResult.getParameters()) {
                final int index = parameterDefinition.getIndex();
                final String value = properties.getProperty(parameterDefinition.getName())
                        .orElse(parameterDefinition.getDefaultValue());
                typeBehaviourFactory.getBehaviour(parameterDefinition.getType())
                        .considerArgument(preparedStatement, index, value);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SqlPreparationException(e);
        }
    }

    private DataSource getDataSource(ExecutionContext context) {
        return context.getDataSource();
    }

}
