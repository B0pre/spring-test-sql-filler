package org.bopre.test.spring.sqlfiller.processor.exception;

import org.bopre.test.spring.sqlfiller.exception.SqlFillerException;

public class IllegalArgumentPlaceholderException extends SqlFillerException {

    public IllegalArgumentPlaceholderException() {
    }

    public IllegalArgumentPlaceholderException(String message) {
        super(message);
    }

    public IllegalArgumentPlaceholderException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentPlaceholderException(Throwable cause) {
        super(cause);
    }

    public IllegalArgumentPlaceholderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
