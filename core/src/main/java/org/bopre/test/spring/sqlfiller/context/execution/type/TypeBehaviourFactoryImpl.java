package org.bopre.test.spring.sqlfiller.context.execution.type;

import org.bopre.test.spring.sqlfiller.exception.SqlPreparationException;
import org.bopre.test.spring.sqlfiller.processor.obj.SupportedType;

import java.sql.SQLException;

public class TypeBehaviourFactoryImpl implements TypeBehaviourFactory {

    @Override
    public TypeBehaviour getBehaviour(SupportedType type) {
        switch (type) {
            case INT:
                return (preparedStatement, index, value) -> handler(() -> preparedStatement.setInt(index, Integer.parseInt(value)));
            case STRING:
                return (preparedStatement, index, value) -> handler(() -> preparedStatement.setString(index, value));
            case DOUBLE:
                return (preparedStatement, index, value) -> handler(() -> preparedStatement.setDouble(index, Double.parseDouble(value)));
            default:
                throw new SqlPreparationException("not supported type: " + type);
        }
    }

    private void handler(ThrowableCommand<SQLException> preparation) {
        try {
            preparation.doCommand();
        } catch (SQLException e) {
            throw new SqlPreparationException(e);
        }
    }

    public interface ThrowableCommand<T extends Exception> {
        void doCommand() throws T;
    }

}
