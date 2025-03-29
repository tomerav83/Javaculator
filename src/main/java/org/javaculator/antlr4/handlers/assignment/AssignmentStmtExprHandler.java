package org.javaculator.antlr4.handlers.assignment;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.exceptions.UnknownOperatorException;
import org.javaculator.antlr4.handlers.interfaces.IStatefulVisitorExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;
import org.javaculator.antlr4.utils.BigDecimalSupport;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class AssignmentStmtExprHandler implements IStatefulVisitorExprHandler<CalcParser.AssignmentStmtContext, CalcParser.ExpressionContext> {
    public static final AssignmentStmtExprHandler INSTANCE = new AssignmentStmtExprHandler();

    @Override
    public Optional<BigDecimal> handle(CalcParser.AssignmentStmtContext ctx,
                                       Snapshot snapshot,
                                       Function<CalcParser.ExpressionContext, BigDecimal> visitor) {
        BigDecimal lhs = getFromSnapshot(ctx, snapshot);
        String op = ctx.getChild(1).getText();

        BigDecimal value = switch (op) {
            case "=" -> visitor.apply(ctx.expression());
            case "+=" -> BigDecimalSupport.add(lhs, visitor.apply(ctx.expression()), true);
            case "-=" -> BigDecimalSupport.sub(lhs, visitor.apply(ctx.expression()), true);
            case "*=" -> BigDecimalSupport.multiply(lhs, visitor.apply(ctx.expression()), true);
            case "/=" -> BigDecimalSupport.div(lhs, visitor.apply(ctx.expression()), true);
            case "%=" -> BigDecimalSupport.mod(lhs, visitor.apply(ctx.expression()), true);
            default ->  throw new UnknownOperatorException(op);
        };

        return Optional.ofNullable(putAndGetCurrent(ctx, snapshot, value));
    }
}
