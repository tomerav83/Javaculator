package org.javaculator.shuntified.utils;

import lombok.AllArgsConstructor;
import org.javaculator.shuntified.pattern.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class InputPreprocessor {
    private String input;

    public static String preprocess(String input) {
        return new InputPreprocessor(input)
                .replaceByPattern(Patterns.PRE_DEC, "predec(%s)")
                .replaceByPattern(Patterns.PRE_INC, "preinc(%s)")
                .replaceByPattern(Patterns.POST_DEC, "postdec(%s)")
                .replaceByPattern(Patterns.POST_INC, "postinc(%s)")
                .replaceNegate();
    }

    private String replaceNegate() {
        char[] characters = input.replace(" ", "").toCharArray();
        Matcher matcher = Patterns.NEGATE.matcher(new String(characters));

        while (matcher.find()) {
            characters[matcher.start()] = '−';
        }

        return new String(characters);
    }

    private InputPreprocessor replaceByPattern(Pattern pattern, String format) {
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            input = input.replace(matcher.group(), format.formatted(matcher.group(1)));
        }

        return this;
    }

    private String finish() {
        return input.replace(" ", "");
    }
}
