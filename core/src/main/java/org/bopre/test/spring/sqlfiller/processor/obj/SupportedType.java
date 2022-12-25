package org.bopre.test.spring.sqlfiller.processor.obj;

public enum SupportedType {
    STRING,
    INT,
    DOUBLE;

    public static SupportedType typeOf(String type) {
        return valueOf(type);
    }

}
