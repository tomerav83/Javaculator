package org.javaculator.tokenization.utils;

import java.util.function.Predicate;
import java.util.regex.Matcher;

public class ErrorlessMatcher {
    public static Integer errorSafeStart(Matcher matcher) {
        try {
            return matcher.start();
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public static Boolean errorSafeGroup(Matcher matcher, String tokenGroup) {
        try {
            return matcher.group(tokenGroup) != null;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public static Boolean errorSafeGroup(Matcher matcher, Predicate<String> predicate) {
        try {
            return predicate.test(matcher.group());
        } catch (IllegalStateException e) {
            return false;
        }
    }
}
