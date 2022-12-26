package org.bopre.test.spring.sqlfiller.utils.exception;

import org.bopre.test.spring.sqlfiller.utils.func.ThrowableCommand;

import java.util.function.Function;

public class ExceptionUtils {
    private ExceptionUtils() {
        //do not create
    }

    public static <E extends Exception, R extends Exception> void reThrowException(
            ThrowableCommand<E> preparation,
            Function<Exception, R> rethrow
    ) throws R {
        try {
            preparation.doCommand();
        } catch (Exception e) {
            throw rethrow.apply(e);
        }
    }
}
