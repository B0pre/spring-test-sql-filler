package org.bopre.test.spring.sqlfiller.context.execution;

import org.bopre.test.spring.sqlfiller.context.processing.SqlPreparation;
import org.bopre.test.spring.sqlfiller.context.processing.SqlTemplateProperties;
import org.bopre.test.spring.sqlfiller.context.processing.impl.SqlPreparationImpl;
import org.bopre.test.spring.sqlfiller.testutils.ExampleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bopre.test.spring.sqlfiller.testutils.ExampleEntity.CREATE_EXAMPLE_TABLE;
import static org.bopre.test.spring.sqlfiller.testutils.ExampleEntity.DROP_EXAMPLE_TABLE;
import static org.bopre.test.spring.sqlfiller.testutils.ExampleEntityTestUtils.getAllExamples;
import static org.bopre.test.spring.sqlfiller.testutils.ExecutionContextTestUtils.contextOf;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(SpringExtension.class)
public abstract class AbstractExecutionStrategyTest {

    @Autowired
    protected DataSource dataSource;

    protected abstract ExecutionStrategy getStrategy();

    @Test
    @Sql(statements = CREATE_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = DROP_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void simpleSqlExecutionSkipNull() {
        getStrategy().executeAll(contextOf(dataSource), Arrays.asList(
                createFullPreparation(null, null, new HashMap<>())
        ));

        final List<ExampleEntity> actual = getAllExamples(dataSource);
        Assertions.assertTrue(actual.isEmpty(), "expected list to be empty");
    }

    @Test
    @Sql(statements = CREATE_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = DROP_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void simpleSqlExecutionSkipEmpty() {
        getStrategy().executeAll(contextOf(dataSource), Arrays.asList(
                createFullPreparation("", "", new HashMap<>())
        ));

        final List<ExampleEntity> actual = getAllExamples(dataSource);
        Assertions.assertTrue(actual.isEmpty(), "expected list to be empty");
    }

    protected final SqlPreparation createFullPreparation(String sql, String cleanup, Map<String, String> args) {
        return SqlPreparationImpl.builder()
                .sqlTemplate(sql)
                .sqlCleanup(cleanup)
                .properties(SqlTemplateProperties.ofMap(args))
                .build();
    }

    protected final SqlPreparation createDirectSql(String sql) {
        return createDirectSql(sql, new HashMap<>());
    }

    protected final SqlPreparation createDirectSql(String sql, Map<String, String> args) {
        return SqlPreparationImpl.builder()
                .sqlTemplate(sql)
                .properties(SqlTemplateProperties.ofMap(args))
                .build();
    }

    protected final SqlPreparation createCleanup(String sql) {
        return createCleanup(sql, new HashMap<>());
    }

    protected final SqlPreparation createCleanup(String sql, Map<String, String> args) {
        return SqlPreparationImpl.builder()
                .sqlCleanup(sql)
                .properties(SqlTemplateProperties.ofMap(args))
                .build();
    }

}
