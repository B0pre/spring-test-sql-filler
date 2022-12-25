package org.bopre.test.spring.sqlfiller.context.execution.type;

import org.bopre.test.spring.sqlfiller.processor.obj.SupportedType;

public interface TypeBehaviourFactory {
    TypeBehaviour getBehaviour(SupportedType type);
}
