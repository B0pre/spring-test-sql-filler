package org.bopre.test.spring.sqlfiller.context.execution.type;

import org.bopre.test.spring.sqlfiller.exception.SqlPreparationException;
import org.bopre.test.spring.sqlfiller.processor.obj.SupportedType;
import org.bopre.test.spring.sqlfiller.utils.exception.ExceptionUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public class TypeBehaviourFactoryImpl implements TypeBehaviourFactory {

    @Override
    public TypeBehaviour getBehaviour(SupportedType type) {
        return NullBehaviourWrap.of(getBehaviourInternal(type));
    }

    private TypeBehaviour getBehaviourInternal(SupportedType type) {
        switch (type) {
            case INT:
                return typeBehaviour(PreparedStatement::setInt, Integer::parseInt);
            case STRING:
                return typeBehaviour(PreparedStatement::setString, Function.identity());
            case DOUBLE:
                return typeBehaviour(PreparedStatement::setDouble, Double::parseDouble);
            default:
                throw new SqlPreparationException("not supported type: " + type);
        }
    }

    private <T> TypeBehaviour typeBehaviour(ParameterSetter<T> setter, Function<String, T> converter) {
        return (preparedStatement, index, value) -> {
            ExceptionUtils.reThrowException(
                    () -> {
                        final T converted = converter.apply(value);
                        setter.set(preparedStatement, index, converted);
                    },
                    SqlPreparationException::new
            );
        };
    }

    private interface ParameterSetter<T> {
        void set(PreparedStatement preparedStatement, int index, T value) throws SQLException;
    }

}
