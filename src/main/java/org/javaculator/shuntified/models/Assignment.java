package org.javaculator.shuntified.models;


import java.util.regex.Matcher;

public record Assignment(String key, String operation, String origin) {
    public static Assignment create(Matcher matcher) {
        return new Assignment(matcher.group(1), matcher.group(2), matcher.group());
    }
}
