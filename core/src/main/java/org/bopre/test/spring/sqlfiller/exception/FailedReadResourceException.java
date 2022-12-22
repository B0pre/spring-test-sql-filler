package org.bopre.test.spring.sqlfiller.exception;

public class FailedReadResourceException extends SqlFillerException {
    public FailedReadResourceException() {
    }

    public FailedReadResourceException(String message) {
        super(message);
    }

    public FailedReadResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedReadResourceException(Throwable cause) {
        super(cause);
    }

    public FailedReadResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
