package org.javaculator.antlr42po.handlers.assignment.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.exceptions.MissingOrNullIdentifierException;
import org.javaculator.antlr42po.handlers.assignment.IAssignExpr;
import org.javaculator.antlr42po.handlers.multiplicative.impl.MultiplicativeExprHandler;
import org.javaculator.antlr42po.utils.ParserRuleContextUtils;

import java.util.Map;
import java.util.function.Function;

public class DFExprHandler implements IAssignExpr {
    public static final DFExprHandler INSTANCE = new DFExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.ExpressionContext ctx) {
        if (ctx.additiveExpr() != null) {
            return Implementation.DF;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.ExpressionContext ctx, Function<Calc2Parser.AdditiveExprContext, Integer> visitor) {
        return visitor.apply(ctx.additiveExpr());
    }

    @Override
    public Integer calculate(Calc2Parser.ExpressionContext ctx,
                             Map<String, Integer> vars,
                             Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.DF.name());
    }
}
