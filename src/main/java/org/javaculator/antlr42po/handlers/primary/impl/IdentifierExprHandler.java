package org.javaculator.antlr42po.handlers.primary.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.exceptions.MissingOrNullIdentifierException;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;
import org.javaculator.antlr42po.utils.ParserRuleContextUtils;

import java.util.Map;
import java.util.function.Function;

public class IdentifierExprHandler implements IPrimaryExpr {
    public static final IdentifierExprHandler INSTANCE = new IdentifierExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.PrimaryExprContext ctx) {
        if (ctx.ID() != null && ctx.getChildCount() == 1) {
            return Implementation.IDENTIFIER;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx) {
        throw new InvalidCalculationImplException(Implementation.IDENTIFIER.name());
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Map<String, Integer> vars) {
        String identifier = ParserRuleContextUtils.getIdentifier(ctx);

        if (!vars.containsKey(identifier) || vars.get(identifier) == null) {
            throw new MissingOrNullIdentifierException(identifier);
        }

        return vars.getOrDefault(identifier, 0);
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.IDENTIFIER.name());
    }
}
