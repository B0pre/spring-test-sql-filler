package org.bopre.test.spring.sqlfiller.context.execution.type;

import org.bopre.test.spring.sqlfiller.exception.SqlPreparationException;
import org.bopre.test.spring.sqlfiller.utils.exception.ExceptionUtils;

import java.sql.PreparedStatement;

public class NullBehaviourWrap implements TypeBehaviour {
    private final TypeBehaviour typeBehaviour;

    private NullBehaviourWrap(TypeBehaviour typeBehaviour) {
        this.typeBehaviour = typeBehaviour;
    }

    @Override
    public void considerArgument(PreparedStatement preparedStatement, int index, String value) {
        if (value != null) {
            typeBehaviour.considerArgument(preparedStatement, index, value);
        } else
            ExceptionUtils.reThrowException(() -> preparedStatement.setObject(index, null), SqlPreparationException::new);
    }

    public static NullBehaviourWrap of(TypeBehaviour typeBehaviour) {
        return new NullBehaviourWrap(typeBehaviour);
    }
}
