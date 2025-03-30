package org.javaculator.antlr4.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;

@UtilityClass
public class RadixUtils {
    public static BigDecimal fromFPString(String literal) {
        literal = trimFPLiteral(literal);
        String lower = literal.toLowerCase();

        if (lower.startsWith("0x") || lower.startsWith("+0x") || lower.startsWith("-0x")) {
            return BigDecimal.valueOf(Double.parseDouble(literal));
        } else {
            return new BigDecimal(literal);
        }
    }

    public static BigDecimal fromString(String literal) {
        literal = trimIntegerLiteral(literal);
        boolean negative = literal.startsWith("-");
        int offset = (literal.startsWith("-") || literal.startsWith("+")) ? 1 : 0;
        int radix = 10;

        if (literal.regionMatches(true, offset, "0x", 0, 2) ||
                literal.regionMatches(true, offset, "0X", 0, 2)) {
            radix = 16;
            offset +=2;
        } else if (literal.regionMatches(true, offset, "0b", 0, 2) ||
                literal.regionMatches(true, offset, "0B", 0, 2)) {
            radix = 2;
            offset +=2;
        } else if (literal.charAt(offset) == '0' && literal.length() > offset + 1) {
            radix = 8;
            offset++;
        }

        BigInteger integer = new BigInteger(literal.substring(offset), radix);

        return negative ? new BigDecimal(integer.negate()) : new BigDecimal(integer);
    }

    private static String trimIntegerLiteral(String literal) {
        return literal.replace("_", "")
                .replace("l", "")
                .replace("L", "");
    }

    private static String trimFPLiteral(String literal) {
        return literal.replace("_", "")
                .replace("f", "")
                .replace("F", "")
                .replace("d", "")
                .replace("D", "");
    }
}
