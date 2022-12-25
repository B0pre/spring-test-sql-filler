package org.bopre.test.spring.sqlfiller.context;

import javax.sql.DataSource;

public interface ExecutionContext {

    DataSource getDataSource();

}
