package org.javaculator.shuntified.models.literal;

import org.apache.commons.lang3.tuple.Pair;

import java.util.regex.MatchResult;

public class LiteralWrapperUtils {
    public static LiteralWrapper handleMatch(MatchResult match, String regex, int radix) {
        try {
            String withoutUnderscores = removeUnderscores(match.group().trim());
            String afterReplace = replaceByRegex(withoutUnderscores, regex);
            Number value = Long.parseLong(afterReplace, radix);

            return new LiteralWrapper(value, match.group(), match.start());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static LiteralWrapper handleFloatMatch(MatchResult match) {
        try {
            String withoutUnderscores = removeUnderscores(match.group());
            String afterReplace = replaceByRegex(withoutUnderscores, "[fFdD]");
            Number value = Double.parseDouble(afterReplace);

            return new LiteralWrapper(value, match.group(), match.start());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String replaceByRegex(String input, String regex) {
        return input.replaceAll(regex, "");
    }

    private static String removeUnderscores(String input) {
        return input.replace("_", "");
    }
}
