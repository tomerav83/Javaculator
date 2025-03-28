package org.javaculator.antlr42po.handlers.assignment.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.exceptions.MissingOrNullIdentifierException;
import org.javaculator.antlr42po.handlers.assignment.IAssignExpr;
import org.javaculator.antlr42po.handlers.multiplicative.impl.MultiplicativeExprHandler;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;
import org.javaculator.antlr42po.utils.ParserRuleContextUtils;

import java.util.Map;
import java.util.function.Function;

public class AssignmentExprHandler implements IAssignExpr {
    public static final AssignmentExprHandler INSTANCE = new AssignmentExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.ExpressionContext ctx) {
        if (ctx.ID() != null) {
            return Implementation.ASSIGNMENT;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.ExpressionContext ctx, Function<Calc2Parser.AdditiveExprContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.ASSIGNMENT.name());
    }

    @Override
    public Integer calculate(Calc2Parser.ExpressionContext ctx,
                             Map<String, Integer> vars,
                             Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        String identifier = ParserRuleContextUtils.getIdentifier(ctx);
        String op =ParserRuleContextUtils.getChild(ctx, 1);

        if (!vars.containsKey(identifier) || vars.get(identifier) == null) {
            throw new MissingOrNullIdentifierException(identifier);
        }

        Integer lhs = vars.get(identifier);
        Integer rhs = visitor.apply(ctx.expression());

        Integer value = switch (op) {
            case "=" -> rhs;
            case "+=" -> lhs + rhs;
            case "-=" -> lhs - rhs;
            case "*=" -> lhs * rhs;
            case "/=" -> lhs / rhs;
            case "%=" -> lhs % rhs;
            default -> throw new RuntimeException("Unknown operator: " + op);
        };

        vars.put(identifier, value);
        return value;
    }
}
