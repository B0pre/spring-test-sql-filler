package org.bopre.test.spring.sqlfiller.context.execution;

import org.bopre.test.spring.sqlfiller.context.execution.strategy.BeforeExecutionStrategyImpl;
import org.bopre.test.spring.sqlfiller.testutils.ExampleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.bopre.test.spring.sqlfiller.testutils.ExampleEntity.CREATE_EXAMPLE_TABLE;
import static org.bopre.test.spring.sqlfiller.testutils.ExampleEntity.DROP_EXAMPLE_TABLE;
import static org.bopre.test.spring.sqlfiller.testutils.ExampleEntityTestUtils.getAllExamples;
import static org.bopre.test.spring.sqlfiller.testutils.ExecutionContextTestUtils.contextOf;

class BeforeExecutionStrategyImplTest extends AbstractExecutionStrategyTest {

    private ExecutionStrategy beforeExecutionStrategy;

    @BeforeEach
    void beforeEach() {
        beforeExecutionStrategy = new BeforeExecutionStrategyImpl();
    }

    @Override
    protected ExecutionStrategy getStrategy() {
        return beforeExecutionStrategy;
    }

    @Test
    @Sql(statements = CREATE_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = DROP_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void simpleSqlExecution() {
        beforeExecutionStrategy.executeAll(contextOf(dataSource), Arrays.asList(
                createDirectSql("insert into example(id,name) values (1, 'sample')")
        ));

        final List<ExampleEntity> expected = Arrays.asList(
                new ExampleEntity(1, "sample")
        );
        final List<ExampleEntity> actual = getAllExamples(dataSource);

        Assertions.assertEquals(expected, actual, "wrong list");
    }

    @Test
    @Sql(statements = CREATE_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = DROP_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void simpleSqlExecutionOrderingNatural() {
        beforeExecutionStrategy.executeAll(contextOf(dataSource), Arrays.asList(
                createDirectSql("insert into example(name) values ('sample0')"),
                createDirectSql("insert into example(name) values ('sample1')"),
                createDirectSql("insert into example(name) values ('sample2')"),
                createDirectSql("insert into example(name) values ('sample3')"),
                createDirectSql("insert into example(name) values ('sample4')")
        ));

        final List<ExampleEntity> expected = Arrays.asList(
                new ExampleEntity(1, "sample0"),
                new ExampleEntity(2, "sample1"),
                new ExampleEntity(3, "sample2"),
                new ExampleEntity(4, "sample3"),
                new ExampleEntity(5, "sample4")
        );
        final List<ExampleEntity> actual = getAllExamples(dataSource);

        Assertions.assertEquals(expected, actual, "wrong list");
    }


    @Test
    @Sql(statements = CREATE_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = DROP_EXAMPLE_TABLE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void simpleSqlExecutionArguments() {
        beforeExecutionStrategy.executeAll(contextOf(dataSource), Arrays.asList(
                createDirectSql("insert into example(id, name, rating) values (#{INT/id:0},#{STRING/name:}, #{DOUBLE/rating:0.0})", new HashMap<String, String>() {{
                    this.put("id", "12");
                    this.put("name", "sample");
                    this.put("rating", "1.1");
                }})
        ));

        final List<ExampleEntity> expected = Arrays.asList(
                new ExampleEntity(12, "sample", 1.1)
        );
        final List<ExampleEntity> actual = getAllExamples(dataSource);

        Assertions.assertEquals(expected, actual, "wrong list");
    }


}