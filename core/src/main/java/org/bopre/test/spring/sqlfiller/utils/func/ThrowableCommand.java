package org.bopre.test.spring.sqlfiller.utils.func;

public interface ThrowableCommand<T extends Exception> {
    void doCommand() throws T;
}
