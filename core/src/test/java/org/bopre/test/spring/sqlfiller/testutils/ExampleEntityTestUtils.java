package org.bopre.test.spring.sqlfiller.testutils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ExampleEntityTestUtils {
    private ExampleEntityTestUtils() {
        //do not create
    }

    public static List<ExampleEntity> getAllExamples(DataSource dataSource) {
        try {
            QueryRunner run = new QueryRunner(dataSource);
            ResultSetHandler<List<ExampleEntity>> entityHandler = createResultSetHandler();
            return run.query("select * from example order by id;", entityHandler);
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }

    public static ResultSetHandler<List<ExampleEntity>> createResultSetHandler() {
        return rs -> {
            final List<ExampleEntity> result = new LinkedList<>();
            while (rs.next()) {
                result.add(
                        new ExampleEntity(
                                rs.getInt("id"),
                                rs.getString("name"),
                                (Double) rs.getObject("rating")
                        )
                );
            }
            return result;
        };
    }

}
