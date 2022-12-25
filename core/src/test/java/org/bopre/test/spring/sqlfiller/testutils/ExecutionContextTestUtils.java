package org.bopre.test.spring.sqlfiller.testutils;

import org.bopre.test.spring.sqlfiller.context.ExecutionContext;

import javax.sql.DataSource;

public class ExecutionContextTestUtils {
    private ExecutionContextTestUtils() {
        //do not create
    }

    public static ExecutionContext contextOf(DataSource dataSource) {
        return () -> dataSource;
    }

}
