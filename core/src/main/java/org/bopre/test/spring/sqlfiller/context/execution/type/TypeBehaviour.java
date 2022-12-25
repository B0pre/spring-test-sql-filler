package org.bopre.test.spring.sqlfiller.context.execution.type;

import java.sql.PreparedStatement;

public interface TypeBehaviour {

    void considerArgument(PreparedStatement preparedStatement, int index, String value);

}
