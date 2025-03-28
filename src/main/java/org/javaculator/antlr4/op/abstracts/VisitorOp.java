package org.javaculator.antlr4.op.abstracts;

import org.antlr.v4.runtime.ParserRuleContext;
import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


public abstract class VisitorOp<CTX extends ParserRuleContext> {
    protected final CTX ctx;

    protected VisitorOp(CTX ctx) {
        this.ctx = ctx;
    }

    protected abstract String getOp();
}
