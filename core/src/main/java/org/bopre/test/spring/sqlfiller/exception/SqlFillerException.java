package org.bopre.test.spring.sqlfiller.exception;

public class SqlFillerException extends RuntimeException {

    public SqlFillerException() {
    }

    public SqlFillerException(String message) {
        super(message);
    }

    public SqlFillerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlFillerException(Throwable cause) {
        super(cause);
    }

    public SqlFillerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
