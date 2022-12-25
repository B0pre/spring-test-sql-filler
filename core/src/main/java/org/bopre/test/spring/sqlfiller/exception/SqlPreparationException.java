package org.bopre.test.spring.sqlfiller.exception;

public class SqlPreparationException extends SqlFillerException {

    public SqlPreparationException() {
    }

    public SqlPreparationException(String message) {
        super(message);
    }

    public SqlPreparationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlPreparationException(Throwable cause) {
        super(cause);
    }

    public SqlPreparationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
