package org.javaculator.antlr42po.utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.Calc2Parser;

public class ParserRuleContextUtils {

    public static <CTX extends ParserRuleContext> Integer getLiteral(CTX ctx) {
        return Integer.valueOf(ctx.getToken(Calc2Parser.INT, 0).getText());
    }

    public static <CTX extends ParserRuleContext> String getIdentifier(CTX ctx) {
        return ctx.getToken(Calc2Parser.ID, 0).getText();
    }

    public static <CTX extends ParserRuleContext> String getChild(CTX ctx, int index) {
        return ctx.getChild(index).getText();
    }

    private static <CTX extends ParserRuleContext> String getToken(CTX ctx, int ttype) {
        return ctx.getToken(ttype, 0).getText();
    }
}
