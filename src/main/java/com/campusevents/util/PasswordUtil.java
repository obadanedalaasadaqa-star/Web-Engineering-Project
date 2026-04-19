package com.campusevents.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    public static boolean verify(String plainPassword, String hashedPassword) {
        if (hashedPassword == null) return false;
        // jBCrypt 0.4 only handles $2a$; normalize $2b$ (functionally identical)
        String normalized = hashedPassword.startsWith("$2b$")
            ? "$2a$" + hashedPassword.substring(4)
            : hashedPassword;
        return BCrypt.checkpw(plainPassword, normalized);
    }
}
