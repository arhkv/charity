package com.example.charityapi.util;

import java.util.regex.Pattern;

public final class EmailValidator {
    private EmailValidator() {}

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isValid(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }
}
