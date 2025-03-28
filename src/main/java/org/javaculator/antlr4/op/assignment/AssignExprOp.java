package org.javaculator.antlr4.op.assignment;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.abstracts.impl.AssignmentCalcOp;
import org.javaculator.antlr4.op.lr.AddSubOp;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.Map;
import java.util.function.Function;

public class AssignExprOp extends AssignmentCalcOp<CalcParser.AssignExprContext> {
    private AssignExprOp(CalcParser.AssignExprContext assignExprContext) {
        super(assignExprContext);
    }

    public static Integer handle(CalcParser.AssignExprContext ctx,
                                 Map<String, Integer> vars,
                                 Function<CalcParser.ExpressionContext, Integer> visitor) {
        return new AssignExprOp(ctx).calculate(vars, visitor);
    }

    @Override
    protected Integer calculate(Map<String, Integer> vars,
                                Function<CalcParser.ExpressionContext, Integer> visitor) {
        return vars.merge(getIdentifier(), visitor.apply(ctx.expression()), Integer::sum);
    }



    @Override
    protected String getOp() {
        throw new IllegalStateException("IdentifierExpr isn't operation oriented");
    }
}
