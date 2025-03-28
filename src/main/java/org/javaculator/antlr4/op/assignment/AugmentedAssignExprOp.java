package org.javaculator.antlr4.op.assignment;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.CalculationException;
import org.javaculator.antlr4.op.abstracts.impl.AssignmentCalcOp;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.Map;
import java.util.function.Function;

public class AugmentedAssignExprOp extends AssignmentCalcOp<CalcParser.AugmentedAssignExprContext> {
    private AugmentedAssignExprOp(CalcParser.AugmentedAssignExprContext ctx) {
        super(ctx);
    }

    public static Integer handle(CalcParser.AugmentedAssignExprContext ctx,
                                 Map<String, Integer> vars,
                                 Function<CalcParser.ExpressionContext, Integer> visitor) {
        return new AugmentedAssignExprOp(ctx).calculate(vars, visitor);
    }

    @Override
    protected Integer calculate( Map<String, Integer> vars, Function<CalcParser.ExpressionContext, Integer> visitor) {
        String identifier = getIdentifier();

        if (isIdentifierMissingOrNull(vars)) {
            throw new CalculationException(CalculationException.Type.MISSING_OR_NULL_IDENTIFIER, identifier);
        }

        Integer current = vars.get(identifier);
        int rhs = visitor.apply(ctx.expression());
        String op = getOp();

        int result = switch (op) {
            case "+=" -> current + rhs;
            case "-=" -> current - rhs;
            case "*=" -> current * rhs;
            case "/=" -> current / rhs;
            case "%=" -> current % rhs;
            default -> throw new RuntimeException("Unknown assignment: " + op);
        };

        vars.put(identifier, result);
        return result;
    }

    @Override
    protected String getOp() {
        return ctx.op.getText();
    }
}
