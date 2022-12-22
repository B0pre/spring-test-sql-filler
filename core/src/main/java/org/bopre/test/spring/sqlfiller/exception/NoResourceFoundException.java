package org.bopre.test.spring.sqlfiller.exception;

public class NoResourceFoundException extends SqlFillerException {
    public NoResourceFoundException() {
    }

    public NoResourceFoundException(String message) {
        super(message);
    }

    public NoResourceFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResourceFoundException(Throwable cause) {
        super(cause);
    }

    public NoResourceFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
