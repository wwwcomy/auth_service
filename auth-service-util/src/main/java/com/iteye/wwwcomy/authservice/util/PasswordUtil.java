package com.iteye.wwwcomy.authservice.util;

import java.util.regex.Pattern;

public class PasswordUtil {
    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern CHAR_PATTERN = Pattern.compile(".*[a-zA-Z].*");

    public static boolean hasNumber(String s) {
        return NUMBER_PATTERN.matcher(s).matches();
    }

    public static boolean hasChar(String s) {
        return CHAR_PATTERN.matcher(s).matches();
    }
}
