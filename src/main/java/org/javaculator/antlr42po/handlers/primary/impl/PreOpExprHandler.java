package org.javaculator.antlr42po.handlers.primary.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.exceptions.MissingOrNullIdentifierException;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;
import org.javaculator.antlr42po.utils.ParserRuleContextUtils;

import java.util.Map;
import java.util.function.Function;

public  class PreOpExprHandler implements IPrimaryExpr {
    public static final PreOpExprHandler INSTANCE = new PreOpExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.PrimaryExprContext ctx) {
        if (ctx.ID() != null && ctx.getChildCount() == 2) {
            return Implementation.PRE_OP;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx) {
        throw new InvalidCalculationImplException(Implementation.PRE_OP);
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Map<String, Integer> vars) {
        String identifier = ParserRuleContextUtils.getIdentifier(ctx);
        String op = ParserRuleContextUtils.getChild(ctx, 1);

        if (!vars.containsKey(identifier) || vars.get(identifier) == null) {
            throw new MissingOrNullIdentifierException(identifier);
        }

        return switch (op) {
            case "--" -> vars.put(identifier, vars.get(identifier) - 1);
            case "++" -> vars.put(identifier, vars.get(identifier) + 1);
            default ->  throw new RuntimeException("Unknown operator: " + op);
        };
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.PRE_OP);
    }
}
