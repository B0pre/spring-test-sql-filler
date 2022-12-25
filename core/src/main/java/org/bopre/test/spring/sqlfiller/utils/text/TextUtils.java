package org.bopre.test.spring.sqlfiller.utils.text;

public class TextUtils {

    private TextUtils() {
        //do not create
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

}
