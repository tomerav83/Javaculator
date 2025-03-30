package org.javaculator.antlr4.utils;

import org.antlr.v4.runtime.ParserRuleContext;

public class ParserCtxUtils {
    public static <CTX extends ParserRuleContext> String  getChild(CTX ctx, int index) {
        return ctx.getChild(index).getText();
    }
}
